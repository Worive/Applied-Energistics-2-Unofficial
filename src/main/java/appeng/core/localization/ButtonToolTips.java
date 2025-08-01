/*
 * This file is part of Applied Energistics 2. Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved. Applied
 * Energistics 2 is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version. Applied Energistics 2 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details. You should have received a copy of the GNU Lesser General Public License along with
 * Applied Energistics 2. If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.core.localization;

import net.minecraft.util.StatCollector;

public enum ButtonToolTips {

    PowerUnits,
    IOMode,
    CondenserOutput,
    RedstoneMode,
    MatchingFuzzy,

    MatchingMode,
    TransferDirection,
    SortOrder,
    SortBy,
    View,

    PartitionStorage,
    Clear,
    FuzzyMode,
    OperationMode,
    TrashController,

    InterfaceBlockingMode,
    InterfaceSmartBlockingMode,
    InterfaceCraftingMode,
    Trash,
    MatterBalls,

    Singularity,
    Read,
    Write,
    ReadWrite,
    AlwaysActive,

    ActiveWithoutSignal,
    ActiveWithSignal,
    ActiveOnPulse,

    EmitLevelsBelow,
    EmitLevelAbove,
    MatchingExact,
    TransferToNetwork,

    TransferToStorageCell,
    ToggleSortDirection,
    SearchMode_Auto,

    SearchMode_Standard,
    SearchMode_NEIAuto,
    SearchMode_NEIStandard,

    SearchMode,
    ItemName,
    NumberOfItems,
    PartitionStorageHint,
    NextPartitionStorageHint,

    ClearSettings,
    StoredItems,
    StoredCraftable,
    Craftable,

    FZPercent_25,
    FZPercent_50,
    FZPercent_75,
    FZPercent_99,
    FZIgnoreAll,

    MoveWhenEmpty,
    MoveWhenWorkIsDone,
    MoveWhenFull,
    Disabled,
    Enable,

    Blocking,
    NonBlocking,
    SmartBlocking,
    NonSmartBlocking,

    LevelType,
    LevelType_Energy,
    LevelType_Item,
    InventoryTweaks,
    TerminalStyle,
    TerminalStyle_Full,
    TerminalStyle_Tall,
    TerminalStyle_Small,
    SaveAsImage,
    HideStored,

    Stash,
    StashDesc,
    Encode,
    EncodeDescription,
    Substitutions,
    BeSubstitutions,
    PatternSlotConfigTitle,
    PatternSlotConfigInfo,
    SubstitutionsDescEnabled,
    SubstitutionsDescDisabled,
    BeSubstitutionsDescEnabled,
    BeSubstitutionsDescDisabled,
    CraftOnly,
    CraftEither,

    Craft,
    Mod,
    UsedPercent,
    DoesntDespawn,
    EmitterMode,
    CraftViaRedstone,
    EmitWhenCrafting,
    ReportInaccessibleItems,
    ReportInaccessibleItemsYes,
    ReportInaccessibleItemsNo,

    TypeFilter,
    TypeFilterShowAll,
    TypeFilterShowItemsOnly,
    TypeFilterShowFluidsOnly,

    BlockPlacement,
    BlockPlacementYes,
    BlockPlacementNo,

    // Used in the tooltips of the items in the terminal, when moused over
    ItemsStored,
    ItemCount,
    ItemsRequestable,
    P2PFrequency,
    SearchStringTooltip,

    SchedulingMode,
    SchedulingModeDefault,
    SchedulingModeRoundRobin,
    SchedulingModeRandom,
    OreFilter,
    OreFilterHint,
    DoublePattern,
    DoublePatternHint,
    DoublePatterns,
    DoublePatternsHint,
    OptimizePatterns,
    OptimizePatternsNoReq,

    SaveSearchString,
    SaveSearchStringYes,
    SaveSearchStringNo,
    CraftingStatus,
    CraftingStatusDesc,
    ToggleMolecularAssemblers,
    ToggleMolecularAssemblersDescOn,
    ToggleMolecularAssemblersDescOff,
    ToggleShowFullInterfaces,
    ToggleShowFullInterfacesOnDesc,
    ToggleShowFullInterfacesOffDesc,
    ToggleShowOnlyInvalidInterface,
    ToggleShowOnlyInvalidInterfaceOnDesc,
    ToggleShowOnlyInvalidInterfaceOffDesc,
    HighlightInterface,
    HighlightInterfaceDesc,
    SearchFieldInputs,
    SearchFieldOutputs,
    SearchFieldNames,

    MultiplyPattern,
    DividePattern,
    MultiplyOrDividePatternHint,

    InsertionModeDefault,
    InsertionModeDefaultDesc,
    InsertionModePreferEmpty,
    InsertionModePreferEmptyDesc,
    InsertionModeOnlyEmpty,
    InsertionModeOnlyEmptyDesc,

    SidelessModeSided,
    SidelessModeSidedDesc,
    SidelessModeSideless,
    SidelessModeSidelessDesc,

    AdvancedBlockingModeDefault,
    AdvancedBlockingModeDefaultDesc,
    AdvancedBlockingModeAll,
    AdvancedBlockingModeAllDesc,
    LockCraftingMode,
    LockCraftingModeNone,
    LockCraftingUntilRedstonePulse,
    LockCraftingWhileRedstoneHigh,
    LockCraftingWhileRedstoneLow,
    LockCraftingUntilResultReturned,
    CraftingModeStandard,
    CraftingModeStandardDesc,
    CraftingModeIgnoreMissing,
    CraftingModeIgnoreMissingDesc,
    ExtraOptions,

    SwitchBytesInfo,
    SwitchBytesInfo_Item,
    SwitchBytesInfo_Fluid,
    SwitchBytesInfo_Essentia,

    PriorityCardMode,
    PriorityCardMode_Edit,
    PriorityCardMode_View,
    PriorityCardMode_Set,
    PriorityCardMode_Inc,
    PriorityCardMode_Dec,
    ToFollow,
    ToUnfollow,

    StringOrder,
    StringOrderNatural,
    StringOrderAlphanum,

    CellRestrictionLabel,
    CellRestrictionHint,

    SearchGotoNext,
    SearchGotoPrev,

    CPUAllowMode,
    CPUAllowAllDesc,
    CPUOnlyAllowPlayerDesc,
    CPUOnlyAllowNonPlayerDesc,

    PinsSection,
    PinsSectionActive,
    PinsSectionDisabled,

    ToggleShowOnlySubstitute,
    ToggleShowOnlySubstituteOnDesc,
    ToggleShowOnlySubstituteOffDesc;

    private final String root;

    ButtonToolTips() {
        this.root = "gui.tooltips.appliedenergistics2";
    }

    ButtonToolTips(final String r) {
        this.root = r;
    }

    public String getLocal() {
        return StatCollector.translateToLocal(this.getUnlocalized());
    }

    public String getUnlocalized() {
        return this.root + '.' + this.toString();
    }
}
