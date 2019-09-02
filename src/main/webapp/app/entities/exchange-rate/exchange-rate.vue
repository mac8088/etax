<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('etaxApp.exchangeRate.home.title')" id="exchange-rate-heading">Exchange Rates</span>
            <router-link :to="{name: 'ExchangeRateCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-exchange-rate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('etaxApp.exchangeRate.home.createLabel')">
                    Create new ExchangeRate
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && exchangeRates && exchangeRates.length === 0">
            <span v-text="$t('etaxApp.exchangeRate.home.notFound')">No exchangeRates found</span>
        </div>
        <div class="table-responsive" v-if="exchangeRates && exchangeRates.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('etaxApp.exchangeRate.cstdCurrencyA')">Cstd Currency A</span></th>
                    <th><span v-text="$t('etaxApp.exchangeRate.cstdCurrencyB')">Cstd Currency B</span></th>
                    <th><span v-text="$t('etaxApp.exchangeRate.rate')">Rate</span></th>
                    <th><span v-text="$t('etaxApp.exchangeRate.startDate')">Start Date</span></th>
                    <th><span v-text="$t('etaxApp.exchangeRate.endDate')">End Date</span></th>
                    <th><span v-text="$t('etaxApp.exchangeRate.ccVersion')">Cc Version</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="exchangeRate in exchangeRates"
                    :key="exchangeRate.id">
                    <td>
                        <router-link :to="{name: 'ExchangeRateView', params: {exchangeRateId: exchangeRate.id}}">{{exchangeRate.id}}</router-link>
                    </td>
                    <td>{{exchangeRate.cstdCurrencyA}}</td>
                    <td>{{exchangeRate.cstdCurrencyB}}</td>
                    <td>{{exchangeRate.rate}}</td>
                    <td>{{exchangeRate.startDate}}</td>
                    <td>{{exchangeRate.endDate}}</td>
                    <td>{{exchangeRate.ccVersion}}</td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'ExchangeRateView', params: {exchangeRateId: exchangeRate.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ExchangeRateEdit', params: {exchangeRateId: exchangeRate.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(exchangeRate)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="etaxApp.exchangeRate.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-exchangeRate-heading" v-bind:title="$t('etaxApp.exchangeRate.delete.question')">Are you sure you want to delete this Exchange Rate?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-exchangeRate" v-text="$t('entity.action.delete')" v-on:click="removeExchangeRate()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./exchange-rate.component.ts">
</script>
