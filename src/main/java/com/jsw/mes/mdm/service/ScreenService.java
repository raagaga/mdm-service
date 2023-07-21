package com.jsw.mes.mdm.service;

import com.jsw.mes.mdm.model.request.ScreenRequest;
import com.jsw.mes.mdm.model.response.ScreenResponse;

import java.util.List;

public interface ScreenService {

    ScreenResponse addScreen(ScreenRequest screenRequest);

    ScreenResponse updateScreen(ScreenRequest screenRequest);

    List<ScreenResponse> deleteScreen(List<Integer> screenIdsList);

    ScreenResponse getScreen(int screenId);

    List<ScreenResponse> getAllScreens(int parentId);
}
