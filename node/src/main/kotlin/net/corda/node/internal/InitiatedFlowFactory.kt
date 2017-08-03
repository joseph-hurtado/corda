package net.corda.node.internal

import net.corda.core.flows.FlowLogic
import net.corda.core.identity.Party

sealed class InitiatedFlowFactory<out F : FlowLogic<*>> {
    abstract fun createFlow(platformVersion: Int, otherParty: Party): F

    data class Core<out F : FlowLogic<*>>(private val factory: (Party, Int) -> F) : InitiatedFlowFactory<F>() {
        override fun createFlow(platformVersion: Int, otherParty: Party): F = factory(otherParty, platformVersion)
    }

    data class CorDapp<out F : FlowLogic<*>>(val flowVersion: Int, private val factory: (Party) -> F) : InitiatedFlowFactory<F>() {
        override fun createFlow(platformVersion: Int, otherParty: Party): F = factory(otherParty)
    }
}

