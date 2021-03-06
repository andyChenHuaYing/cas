package org.apereo.cas.web.flow;

import org.springframework.webflow.engine.ActionState;
import org.springframework.webflow.engine.Flow;

/**
 * The {@link TrustedAuthenticationWebflowConfigurer} is responsible for
 * adjusting the CAS webflow context for trusted authn integration.
 *
 * @author Misagh Moayyed
 * @since 4.2
 */
public class TrustedAuthenticationWebflowConfigurer extends AbstractCasWebflowConfigurer {

    @Override
    protected void doInitialize() throws Exception {
        final Flow flow = getLoginFlow();
        final ActionState actionState = createActionState(flow, "remoteAuthenticate",
                createEvaluateAction("principalFromRemoteUserAction"));
        actionState.getTransitionSet().add(createTransition(CasWebflowConstants.TRANSITION_ID_SUCCESS,
                CasWebflowConstants.TRANSITION_ID_SEND_TICKET_GRANTING_TICKET));
        actionState.getTransitionSet().add(createTransition(CasWebflowConstants.TRANSITION_ID_ERROR,
                getStartState(flow).getId()));
        setStartState(flow, actionState);
    }
}
