package com.company.ksena.web.screens.point;

import com.haulmont.cuba.gui.screen.*;
import com.company.ksena.entity.point.Point;

@UiController("ksena_Point.browse")
@UiDescriptor("point-browse.xml")
@LookupComponent("pointsTable")
@LoadDataBeforeShow
public class PointBrowse extends StandardLookup<Point> {
}