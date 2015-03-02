package com.jingge.dw.sse.view;

import io.dropwizard.views.View;

/**
 * Created by jing.ge on 02.03.2015.
 */
public class SSEView extends View {

    private String sseUrl;

    public SSEView(String sseUrl) {
        super("sseview.ftl");
        this.sseUrl = sseUrl;
    }

    public String getSseUrl() {
        return sseUrl;
    }
}
