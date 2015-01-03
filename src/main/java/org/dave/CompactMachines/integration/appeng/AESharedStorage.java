package org.dave.CompactMachines.integration.appeng;

import net.minecraft.nbt.NBTTagCompound;

import org.dave.CompactMachines.handler.SharedStorageHandler;
import org.dave.CompactMachines.integration.AbstractSharedStorage;
import org.dave.CompactMachines.reference.Reference;
import org.dave.CompactMachines.utility.WorldUtils;

import appeng.api.AEApi;
import appeng.api.exceptions.FailedConnection;
import appeng.api.networking.IGridNode;

public class AESharedStorage extends AbstractSharedStorage {
	public int			coord;
	public int			side;

	public IGridNode	machineNode;
	public IGridNode	interfaceNode;

	public boolean		isConnected	= false;

	public AESharedStorage(SharedStorageHandler storageHandler, int coord, int side) {
		super(storageHandler, coord, side);

		this.side = side;
		this.coord = coord;
	}

	@Override
	public String type() {
		return "appeng";
	}

	public void connectNodes() {
		//LogHelper.info("ConnectNodes called for side: " + ForgeDirection.getOrientation(side));

		if (interfaceNode == null || machineNode == null) {
			return;
		}

		if (isConnected) {
			return;
		}

		if (!Reference.AE_AVAILABLE) {
			return;
		}

		try {
			AEApi.instance().createGridConnection(interfaceNode, machineNode);
			isConnected = true;
		} catch (FailedConnection e) {
			e.printStackTrace();
		}
	}

	public IGridNode getInterfaceNode(CMGridBlock gridBlock) {
		if (interfaceNode == null) {
			interfaceNode = AEApi.instance().createGridNode(gridBlock);
			interfaceNode.updateState();
		}
		connectNodes();
		return interfaceNode;
	}

	public IGridNode getMachineNode(CMGridBlock gridBlock) {
		if (machineNode == null) {
			machineNode = AEApi.instance().createGridNode(gridBlock);
			machineNode.updateState();

			// Update neighbor blocks since we are now having a gridnode
			WorldUtils.updateNeighborAEGrids(gridBlock.getLocation().getWorld(), gridBlock.getLocation().x, gridBlock.getLocation().y, gridBlock.getLocation().z);
		}
		connectNodes();
		return machineNode;
	}

	@Override
	public NBTTagCompound saveToTag() {
		NBTTagCompound compound = prepareTagCompound();
		return compound;
	}

	@Override
	public void loadFromTag(NBTTagCompound tag) {
		loadHoppingModeFromCompound(tag);
	}
}
