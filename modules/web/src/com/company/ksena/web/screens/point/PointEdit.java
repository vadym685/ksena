package com.company.ksena.web.screens.point;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.point.Point;

@UiController("ksena_Point.edit")
@UiDescriptor("point-edit.xml")
@EditedEntityContainer("pointDc")
@LoadDataBeforeShow
public class PointEdit extends StandardEditor<Point> {
}