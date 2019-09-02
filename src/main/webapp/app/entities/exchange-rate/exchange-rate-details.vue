<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="exchangeRate">
                <h2 class="jh-entity-heading"><span v-text="$t('etaxApp.exchangeRate.detail.title')">ExchangeRate</span> {{exchangeRate.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('etaxApp.exchangeRate.cstdCurrencyA')">Cstd Currency A</span>
                    </dt>
                    <dd>
                        <span>{{exchangeRate.cstdCurrencyA}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.exchangeRate.cstdCurrencyB')">Cstd Currency B</span>
                    </dt>
                    <dd>
                        <span>{{exchangeRate.cstdCurrencyB}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.exchangeRate.rate')">Rate</span>
                    </dt>
                    <dd>
                        <span>{{exchangeRate.rate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.exchangeRate.startDate')">Start Date</span>
                    </dt>
                    <dd>
                        <span>{{exchangeRate.startDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.exchangeRate.endDate')">End Date</span>
                    </dt>
                    <dd>
                        <span>{{exchangeRate.endDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.exchangeRate.ccVersion')">Cc Version</span>
                    </dt>
                    <dd>
                        <span>{{exchangeRate.ccVersion}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="exchangeRate.id" :to="{name: 'ExchangeRateEdit', params: {exchangeRateId: exchangeRate.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./exchange-rate-details.component.ts">
</script>
