package com.implicitly.security;

import com.implicitly.constants.Constants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Emil Murzakaev.
 */
@Slf4j
public class OAuthFeignRequestInterceptor implements RequestInterceptor {

    /**
     * Тег используемый при логировании.
     */
    private static final String LOG_TAG = "[OAUTH_FEIGN_REQUEST_INTERCEPTOR] ::";

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(RequestTemplate template) {
        if (isOAuthAuthenticationPresent()) {
            String token = SecurityTokenHolder.getToken();
            if (template.headers().containsKey(Constants.AUTHORIZATION)) {
                log.warn(
                        "{} the Authorization token has been already set",
                        LOG_TAG
                );
                if (!template.headers().get(Constants.AUTHORIZATION).equals(token)) {
                    template.header(Constants.AUTHORIZATION, Constants.BEARER + " " + token);
                }
            } else {
                if (token == null) {
                    log.warn(
                            "{} could not obtain existing token for request, if it is a non secured request ignore it.",
                            LOG_TAG
                    );
                } else {
                    log.debug(
                            "{} constructing Header {} for Token {}",
                            LOG_TAG,
                            Constants.AUTHORIZATION, Constants.BEARER
                    );
                    template.header(Constants.AUTHORIZATION, Constants.BEARER + " " + token);
                }
            }
        } else {
            log.warn(
                    "{} could not obtain existing token for request, if it is a non secured request ignore it.",
                    LOG_TAG
            );
        }
    }

    /**
     * Проверка, что текущий исполняемый поток содержит данные {@link UserPrincipal}
     *
     * @return <code>true</code>, если текущий исполняемый поток содержит данные {@link UserPrincipal},
     * иначе <code>false</code>
     */
    private boolean isOAuthAuthenticationPresent() {
        boolean isContextPresent = SecurityContextHolder.getContext().getAuthentication() != null;
        boolean isDetailsISNotNull = SecurityContextHolder.getContext().getAuthentication().getDetails() != null;
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        boolean isInstanceOfUserDetails = authentication.getPrincipal() instanceof UserPrincipal;
        return isContextPresent && isDetailsISNotNull && isInstanceOfUserDetails;
    }

}
