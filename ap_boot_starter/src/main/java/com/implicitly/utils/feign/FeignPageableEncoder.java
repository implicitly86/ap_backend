/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.utils.feign;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Реализация {@link Encoder} для возможности использования {@link Pageable} в запросах {@link FeignClient}.
 *
 * @author Emil Murzakaev.
 */
public class FeignPageableEncoder implements Encoder {

    /**
     * Параметр запроса <strong>page</strong>
     */
    private static final String PAGE_PARAM = "page";

    /**
     * Параметр запроса <strong>size</strong>
     */
    private static final String SIZE_PARAM = "size";

    /**
     * Параметр запроса <strong>sort</strong>
     */
    private static final String SORT_PARAM = "sort";

    /**
     * {@link Encoder}
     */
    private final Encoder delegate;

    public FeignPageableEncoder(Encoder delegate) {
        this.delegate = delegate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encode(Object object,
                       Type bodyType,
                       RequestTemplate template) throws EncodeException {
        if (object instanceof Pageable) {
            Pageable pageable = (Pageable) object;
            template.query(PAGE_PARAM, pageable.getPageNumber() + StringUtils.EMPTY);
            template.query(SIZE_PARAM, pageable.getPageSize() + StringUtils.EMPTY);

            if (pageable.getSort() != null) {
                Collection<String> existingSorts = template.queries().get(SORT_PARAM);
                List<String> sortQueries = existingSorts != null ? new ArrayList<>(existingSorts) : new ArrayList<>();
                for (Sort.Order order : pageable.getSort()) {
                    sortQueries.add(order.getProperty() + "," + order.getDirection());
                }
                template.query(SORT_PARAM, sortQueries);
            }
        } else {
            delegate.encode(object, bodyType, template);
        }
    }

}
