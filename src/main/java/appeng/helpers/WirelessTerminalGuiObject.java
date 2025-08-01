/*
 * This file is part of Applied Energistics 2. Copyright (c) 2013 - 2015, AlgorithmX2, All rights reserved. Applied
 * Energistics 2 is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version. Applied Energistics 2 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details. You should have received a copy of the GNU Lesser General Public License along with
 * Applied Energistics 2. If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.helpers;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import appeng.api.AEApi;
import appeng.api.config.AccessRestriction;
import appeng.api.config.Actionable;
import appeng.api.config.PowerMultiplier;
import appeng.api.features.ILocatable;
import appeng.api.features.IWirelessTermHandler;
import appeng.api.implementations.guiobjects.IPortableCell;
import appeng.api.implementations.tiles.IViewCellStorage;
import appeng.api.implementations.tiles.IWirelessAccessPoint;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.networking.IMachineSet;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.networking.security.IActionHost;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.storage.IMEMonitor;
import appeng.api.storage.IMEMonitorHandlerReceiver;
import appeng.api.storage.ITerminalPins;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IItemList;
import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;
import appeng.api.util.IConfigManager;
import appeng.container.interfaces.IInventorySlotAware;
import appeng.items.contents.PinsHandler;
import appeng.items.contents.PinsHolder;
import appeng.items.contents.WirelessTerminalViewCells;
import appeng.tile.networking.TileWireless;

public class WirelessTerminalGuiObject
        implements IPortableCell, IActionHost, IInventorySlotAware, IViewCellStorage, ITerminalPins {

    private final ItemStack effectiveItem;
    private final IWirelessTermHandler wth;
    private final String encryptionKey;
    private final EntityPlayer myPlayer;
    private IGrid targetGrid;
    private IStorageGrid sg;
    private IMEMonitor<IAEItemStack> itemStorage;
    private IWirelessAccessPoint myWap;
    private double sqRange = Double.MAX_VALUE;
    private double myRange = Double.MAX_VALUE;
    private final int inventorySlot;
    private final WirelessTerminalViewCells viewCells;
    private final PinsHolder pinsInv;

    public WirelessTerminalGuiObject(final IWirelessTermHandler wh, final ItemStack is, final EntityPlayer ep,
            final World w, final int x, final int y, final int z) {
        this.encryptionKey = wh.getEncryptionKey(is);
        this.effectiveItem = is;
        this.myPlayer = ep;
        this.wth = wh;
        this.inventorySlot = x;
        this.viewCells = new WirelessTerminalViewCells(is);
        pinsInv = new PinsHolder(is);

        ILocatable obj = null;

        try {
            final long encKey = Long.parseLong(this.encryptionKey);
            obj = AEApi.instance().registries().locatable().getLocatableBy(encKey);
        } catch (final NumberFormatException err) {
            // :P
        }

        if (obj instanceof IGridHost) {
            final IGridNode n = ((IGridHost) obj).getGridNode(ForgeDirection.UNKNOWN);
            if (n != null) {
                this.targetGrid = n.getGrid();
                if (this.targetGrid != null) {
                    this.sg = this.targetGrid.getCache(IStorageGrid.class);
                    if (this.sg != null) {
                        this.itemStorage = this.sg.getItemInventory();
                    }
                }
            }
        }
    }

    public double getRange() {
        return this.myRange;
    }

    @Override
    public IMEMonitor<IAEItemStack> getItemInventory() {
        if (this.sg == null) {
            return null;
        }
        return this.sg.getItemInventory();
    }

    @Override
    public IMEMonitor<IAEFluidStack> getFluidInventory() {
        if (this.sg == null) {
            return null;
        }
        return this.sg.getFluidInventory();
    }

    @Override
    public void addListener(final IMEMonitorHandlerReceiver<IAEItemStack> l, final Object verificationToken) {
        if (this.itemStorage != null) {
            this.itemStorage.addListener(l, verificationToken);
        }
    }

    @Override
    public void removeListener(final IMEMonitorHandlerReceiver<IAEItemStack> l) {
        if (this.itemStorage != null) {
            this.itemStorage.removeListener(l);
        }
    }

    @Override
    public IItemList<IAEItemStack> getAvailableItems(final IItemList out, int iteration) {
        if (this.itemStorage != null) {
            return this.itemStorage.getAvailableItems(out, iteration);
        }
        return out;
    }

    @Override
    public IAEItemStack getAvailableItem(@Nonnull IAEItemStack request, int iteration) {
        if (this.itemStorage != null) {
            return this.itemStorage.getAvailableItem(request, iteration);
        }
        return null;
    }

    @Override
    public IItemList<IAEItemStack> getStorageList() {
        if (this.itemStorage != null) {
            return this.itemStorage.getStorageList();
        }
        return null;
    }

    @Override
    public AccessRestriction getAccess() {
        if (this.itemStorage != null) {
            return this.itemStorage.getAccess();
        }
        return AccessRestriction.NO_ACCESS;
    }

    @Override
    public boolean isPrioritized(final IAEItemStack input) {
        if (this.itemStorage != null) {
            return this.itemStorage.isPrioritized(input);
        }
        return false;
    }

    @Override
    public boolean canAccept(final IAEItemStack input) {
        if (this.itemStorage != null) {
            return this.itemStorage.canAccept(input);
        }
        return false;
    }

    @Override
    public int getPriority() {
        if (this.itemStorage != null) {
            return this.itemStorage.getPriority();
        }
        return 0;
    }

    @Override
    public int getSlot() {
        if (this.itemStorage != null) {
            return this.itemStorage.getSlot();
        }
        return 0;
    }

    @Override
    public boolean validForPass(final int i) {
        return this.itemStorage.validForPass(i);
    }

    @Override
    public IAEItemStack injectItems(final IAEItemStack input, final Actionable type, final BaseActionSource src) {
        if (this.itemStorage != null) {
            return this.itemStorage.injectItems(input, type, src);
        }
        return input;
    }

    @Override
    public IAEItemStack extractItems(final IAEItemStack request, final Actionable mode, final BaseActionSource src) {
        if (this.itemStorage != null) {
            return this.itemStorage.extractItems(request, mode, src);
        }
        return null;
    }

    @Override
    public StorageChannel getChannel() {
        if (this.itemStorage != null) {
            return this.itemStorage.getChannel();
        }
        return StorageChannel.ITEMS;
    }

    @Override
    public double extractAEPower(final double amt, final Actionable mode, final PowerMultiplier usePowerMultiplier) {
        if (this.wth != null && this.effectiveItem != null) {
            if (mode == Actionable.SIMULATE) {
                return this.wth.hasPower(this.myPlayer, amt, this.effectiveItem) ? amt : 0;
            }
            return this.wth.usePower(this.myPlayer, amt, this.effectiveItem) ? amt : 0;
        }
        return 0.0;
    }

    @Override
    public ItemStack getItemStack() {
        return this.effectiveItem;
    }

    @Override
    public IConfigManager getConfigManager() {
        return this.wth.getConfigManager(this.effectiveItem);
    }

    @Override
    public IGridNode getGridNode(final ForgeDirection dir) {
        return this.getActionableNode();
    }

    @Override
    public AECableType getCableConnectionType(final ForgeDirection dir) {
        return AECableType.NONE;
    }

    @Override
    public void securityBreak() {}

    @Override
    public IGridNode getActionableNode() {
        this.rangeCheck();
        if (this.myWap != null) {
            return this.myWap.getActionableNode();
        }
        return null;
    }

    public boolean rangeCheck() {
        this.sqRange = this.myRange = Double.MAX_VALUE;

        if (this.targetGrid != null && this.itemStorage != null) {
            if (this.myWap != null) {
                if (this.myWap.getGrid() == this.targetGrid) {
                    if (this.testWap(this.myWap)) {
                        return true;
                    }
                }
                return false;
            }

            final IMachineSet tw = this.targetGrid.getMachines(TileWireless.class);

            this.myWap = null;

            for (final IGridNode n : tw) {
                final IWirelessAccessPoint wap = (IWirelessAccessPoint) n.getMachine();
                if (this.testWap(wap)) {
                    this.myWap = wap;
                }
            }

            return this.myWap != null;
        }
        return false;
    }

    private boolean testWap(final IWirelessAccessPoint wap) {
        double rangeLimit = wap.getRange();
        rangeLimit *= rangeLimit;

        final DimensionalCoord dc = wap.getLocation();

        if (dc.getWorld() == this.myPlayer.worldObj) {
            final double offX = dc.x - this.myPlayer.posX;
            final double offY = dc.y - this.myPlayer.posY;
            final double offZ = dc.z - this.myPlayer.posZ;

            final double r = offX * offX + offY * offY + offZ * offZ;
            if (r < rangeLimit && this.sqRange > r) {
                if (wap.isActive()) {
                    this.sqRange = r;
                    this.myRange = Math.sqrt(r);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getInventorySlot() {
        return this.inventorySlot;
    }

    @Override
    public IInventory getViewCellStorage() {
        return this.viewCells;
    }

    @Override
    public PinsHandler getPinsHandler(EntityPlayer player) {
        return pinsInv.getHandler(myPlayer);
    }

    @Override
    public IGrid getGrid() {
        return targetGrid;
    }
}
