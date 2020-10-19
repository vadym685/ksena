package com.company.ksena.web.screens.template;

import com.company.ksena.entity.cleaning_map.CleaningPosition;
import com.company.ksena.entity.cleaning_map.PositionWrapper;
import com.company.ksena.entity.cleaning_map.Room;
import com.company.ksena.web.screens.room.RoomBrowse;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.SplitPanel;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.data.GroupInfo;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.template.Template;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.v7.ui.AbstractSelect;
import org.checkerframework.checker.units.qual.C;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@UiController("ksena_Template.edit")
@UiDescriptor("template-edit.xml")
@EditedEntityContainer("templateDc")
@LoadDataBeforeShow
public class TemplateEdit extends StandardEditor<Template> {

    @Inject
    private SplitPanel mainSplit;
    @Inject
    private Button addPosition;
    @Inject
    private Button ok;
    @Inject
    private Table<CleaningPosition> possitionTable;
    @Inject
    private GroupTable<PositionWrapper> cleaningMapTable;
    @Inject
    private DataManager dataManager;
    @Inject
    private Metadata metadata;
    @Inject
    private CollectionPropertyContainer<PositionWrapper> cleaningMapDc;
    @Inject
    private Button cleaningMapPositionUp;
    @Inject
    private Button cleaningMapPositionDown;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private Dialogs dialogs;
    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe("addPosition")
    public void onAddPositionClick(Button.ClickEvent event) {
        mainSplit.setSplitPosition(65);
        addPosition.setVisible(false);
        ok.setVisible(true);
    }

    @Subscribe("ok")
    public void onOkClick(Button.ClickEvent event) {
        mainSplit.setSplitPosition(100);
        addPosition.setVisible(true);
        ok.setVisible(false);
    }

    @Subscribe
    public void onInit(InitEvent event) {
        possitionTable.withUnwrapped(com.vaadin.v7.ui.Table.class, table ->
                table.setDragMode(com.vaadin.v7.ui.Table.TableDragMode.MULTIROW)
        );

        cleaningMapTable.withUnwrapped(com.vaadin.v7.ui.Table.class, table -> {
            table.setDragMode(com.vaadin.v7.ui.Table.TableDragMode.MULTIROW);
            table.setDropHandler(new DropHandler() {
                @Override
                public void drop(DragAndDropEvent event) {

                    UUID itemId = (UUID) event.getTransferable().getData("itemId");
                    GroupInfo<Room> targetId = (GroupInfo) ((AbstractSelect.AbstractSelectTargetDetails) event.getTargetDetails()).getItemIdOver();
                    if (targetId != null) {
                        Room room = (Room) targetId.getValue();

                        removeAllNullPositions(room);

                        CleaningPosition cleaningPosition = null;
                        try {
                            cleaningPosition = dataManager.load(CleaningPosition.class).id(itemId).one();
                        } catch (Exception ignored) {
                        }

                        if (cleaningPosition != null) {
                            PositionWrapper positionWrapper = metadata.create(PositionWrapper.class);
                            positionWrapper.setPosition(cleaningPosition);
                            positionWrapper.setRoomName(room);
                            positionWrapper.setPriorityCleaningPosition((int) cleaningMapDc.getItems()
                                    .stream()
                                    .filter(wrapper -> wrapper.getRoomName().equals(room))
                                    .count() + 1
                            );
                            positionWrapper.setTemplate(TemplateEdit.super.getEditedEntity());

                            cleaningMapDc.getMutableItems().add(positionWrapper);
                        } else {
                            PositionWrapper positionWrapper = cleaningMapDc.getItemOrNull(itemId);

                            if (positionWrapper != null && !positionWrapper.getRoomName().equals(room)) {
                                renumberAllPositions(positionWrapper);

                                positionWrapper.setPriorityCleaningPosition((int) cleaningMapDc.getItems()
                                        .stream()
                                        .filter(wrapper -> wrapper.getRoomName().equals(room))
                                        .count() + 1
                                );
                                positionWrapper.setRoomName(room);

                                cleaningMapDc.getMutableItems().remove(positionWrapper);
                                cleaningMapDc.getMutableItems().add(positionWrapper);
                            }
                        }
                    }
                }

                @Override
                public AcceptCriterion getAcceptCriterion() {
                    return AcceptAll.get();
                }
            });
        });
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        cleaningMapDc.getMutableItems().sort(Comparator.comparing(PositionWrapper::getPriorityCleaningPosition));
    }

    @Subscribe("excludePosition")
    public void onExcludePositionClick(Button.ClickEvent event) {
        renumberAllPositions(cleaningMapTable.getSingleSelected());
        cleaningMapDc.getMutableItems().get(cleaningMapDc.getItems().indexOf(cleaningMapTable.getSingleSelected())).setTemplate(null);
        cleaningMapDc.getMutableItems().remove(cleaningMapTable.getSingleSelected());
    }

    private void renumberAllPositions(PositionWrapper positionWrapper) {
        List<PositionWrapper> wrappers = cleaningMapDc.getItems()
                .stream()
                .filter(wrapper -> wrapper.getRoomName().equals(positionWrapper.getRoomName()))
                .collect(Collectors.toList());

        for (int i = wrappers.indexOf(positionWrapper); i < wrappers.size(); i++){
            wrappers.get(i).setPriorityCleaningPosition(i);
        }
    }

    private void removeAllNullPositions(Room room) {
        List<PositionWrapper> wrappers = cleaningMapDc.getItems()
                .stream()
                .filter(wrapper -> wrapper.getPosition() == null && wrapper.getRoomName().equals(room))
                .collect(Collectors.toList());

        cleaningMapDc.getMutableItems().removeAll(wrappers);

    }

    @Subscribe("cleaningMapPositionUp")
    public void onCleaningMapPositionUpClick(Button.ClickEvent event) {
        if (cleaningMapTable.getSingleSelected() != null) {
            throwUpper(cleaningMapTable.getSingleSelected());
        }
    }

    private void throwUpper(PositionWrapper wrapperToUp) {
        PositionWrapper wrapperToDown = cleaningMapDc.getMutableItems()
                .stream()
                .filter(positionWrapper -> positionWrapper.getRoomName().equals(wrapperToUp.getRoomName()))
                .collect(Collectors.toList())
                .get(wrapperToUp.getPriorityCleaningPosition() - 2);

        wrapperToDown.setPriorityCleaningPosition(wrapperToDown.getPriorityCleaningPosition() + 1);
        wrapperToUp.setPriorityCleaningPosition(wrapperToUp.getPriorityCleaningPosition() - 1);

        cleaningMapDc.getMutableItems().sort(Comparator.comparing(PositionWrapper::getPriorityCleaningPosition));
        setEnabledForButtons();
    }

    @Subscribe("cleaningMapPositionDown")
    public void onCleaningMapPositionDownClick(Button.ClickEvent event) {
        PositionWrapper selected = cleaningMapTable.getSingleSelected();
        if(selected != null) {
            PositionWrapper wrapperToDown = cleaningMapDc.getItems()
                    .stream()
                    .filter(wrapper -> wrapper.getRoomName().equals(selected.getRoomName()))
                    .collect(Collectors.toList())
                    .get(selected.getPriorityCleaningPosition());
            throwUpper(wrapperToDown);
        }
    }

    @Subscribe("cleaningMapTable")
    public void onCleaningMapTableSelection(Table.SelectionEvent<PositionWrapper> event) {
        setEnabledForButtons();
    }

    private void setEnabledForButtons() {
        PositionWrapper wrapper = cleaningMapTable.getSingleSelected();
        cleaningMapPositionUp.setEnabled(wrapper != null && wrapper.getPriorityCleaningPosition() != 1);

        int lastIndex = (int) cleaningMapDc.getItems()
                .stream()
                .filter(positionWrapper -> {
                    if (wrapper != null) {
                        return positionWrapper.getRoomName().equals(wrapper.getRoomName());
                    }
                    return false;
                })
                .count();
        cleaningMapPositionDown.setEnabled(
                wrapper != null && wrapper.getPriorityCleaningPosition() != lastIndex
        );

        if (wrapper == null) {
            cleaningMapPositionUp.setEnabled(false);
            cleaningMapPositionDown.setEnabled(false);
        }
    }

    @Subscribe("cleaningMapPositionAddRoom")
    public void onCleaningMapPositionAddRoomClick(Button.ClickEvent event) {
        RoomBrowse selectRoom = screenBuilders.lookup(Room.class, this)
                .withScreenClass(RoomBrowse.class)
                .withOpenMode(OpenMode.DIALOG)
                .withAfterCloseListener(e -> {
                    if (((StandardCloseAction) e.getCloseAction()).getActionId().equals("select")) {
                        Room room = e.getScreen().getSelectedRoom();

                        for (PositionWrapper wrapper : cleaningMapDc.getItems()) {
                            if (wrapper.getRoomName().equals(room)) {
                                dialogs.createExceptionDialog()
                                        .withCaption(messageBundle.getMessage("warning"))
                                        .withThrowable(new Throwable())
                                        .withMessage(messageBundle.getMessage("roomAlreadyAdded"))
                                        .show();
                                return;
                            }
                        }

                        PositionWrapper positionWrapper = metadata.create(PositionWrapper.class);
                        positionWrapper.setPosition(null);
                        positionWrapper.setPriorityCleaningPosition((int) cleaningMapDc.getItems()
                                .stream()
                                .filter(wrapper -> wrapper.getRoomName().equals(room))
                                .count() + 1);
                        positionWrapper.setRoomName(room);

                        cleaningMapDc.getMutableItems().add(positionWrapper);
                    }
                })
                .withSelectHandler(e -> {
                })
                .build();
        selectRoom.show();
    }

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        this.getEditedEntity().setDateOfUpdate(LocalDateTime.now());
        cleaningMapDc.getItems().forEach(wrapper -> event.getDataContext().merge(wrapper));
    }
}