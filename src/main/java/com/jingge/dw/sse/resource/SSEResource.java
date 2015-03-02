package com.jingge.dw.sse.resource;


import com.jingge.dw.sse.view.SSEView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/realtime")
@Produces(MediaType.TEXT_HTML)
public class SSEResource {
    private String sseUrl;

    public SSEResource(String sseUrl) {
        this.sseUrl = sseUrl;
    }

    @GET
    public SSEView getSSEView() {
        return new SSEView(sseUrl);
    }
}
