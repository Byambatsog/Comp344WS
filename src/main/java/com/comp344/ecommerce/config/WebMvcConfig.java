package com.comp344.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import org.springframework.web.servlet.view.xml.MarshallingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Byambatsog on 9/27/16.
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ContentNegotiatingViewResolver viewResolver() {

        ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
        viewResolver.setDefaultContentType(MediaType.APPLICATION_JSON);
        viewResolver.setIgnoreAcceptHeader(true);
        viewResolver.setFavorPathExtension(true);
        Map<String, String> mediaTypes = new HashMap<String, String>();
        mediaTypes.put("xml", "application/xml");
        mediaTypes.put("json", "application/json");
        viewResolver.setMediaTypes(mediaTypes);
        viewResolver.setFavorParameter(true);
        MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
        MarshallingView xmlView = new MarshallingView();
        xmlView.setMarshaller(new XStreamMarshaller());
        List<View> views = new ArrayList<View>();
        views.add(jsonView);
        views.add(xmlView);
        viewResolver.setDefaultViews(views);
        return viewResolver;
    }
}
