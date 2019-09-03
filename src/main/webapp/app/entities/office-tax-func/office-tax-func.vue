<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('etaxApp.officeTaxFunc.home.title')" id="office-tax-func-heading">Office Tax Funcs</span>
            <router-link :to="{name: 'OfficeTaxFuncCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-office-tax-func">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('etaxApp.officeTaxFunc.home.createLabel')">
                    Create new OfficeTaxFunc
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
        <div class="alert alert-warning" v-if="!isFetching && officeTaxFuncs && officeTaxFuncs.length === 0">
            <span v-text="$t('etaxApp.officeTaxFunc.home.notFound')">No officeTaxFuncs found</span>
        </div>
        <div class="table-responsive" v-if="officeTaxFuncs && officeTaxFuncs.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('etaxApp.officeTaxFunc.taxOfficeId')">Tax Office Id</span></th>
                    <th><span v-text="$t('etaxApp.officeTaxFunc.funcOfficeId')">Func Office Id</span></th>
                    <th><span v-text="$t('etaxApp.officeTaxFunc.startDate')">Start Date</span></th>
                    <th><span v-text="$t('etaxApp.officeTaxFunc.endDate')">End Date</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="officeTaxFunc in officeTaxFuncs"
                    :key="officeTaxFunc.id">
                    <td>
                        <router-link :to="{name: 'OfficeTaxFuncView', params: {officeTaxFuncId: officeTaxFunc.id}}">{{officeTaxFunc.id}}</router-link>
                    </td>
                    <td>{{officeTaxFunc.taxOfficeId}}</td>
                    <td>{{officeTaxFunc.funcOfficeId}}</td>
                    <td>{{officeTaxFunc.startDate}}</td>
                    <td>{{officeTaxFunc.endDate}}</td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'OfficeTaxFuncView', params: {officeTaxFuncId: officeTaxFunc.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'OfficeTaxFuncEdit', params: {officeTaxFuncId: officeTaxFunc.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(officeTaxFunc)"
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
            <span slot="modal-title"><span id="etaxApp.officeTaxFunc.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-officeTaxFunc-heading" v-bind:title="$t('etaxApp.officeTaxFunc.delete.question')">Are you sure you want to delete this Office Tax Func?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-officeTaxFunc" v-text="$t('entity.action.delete')" v-on:click="removeOfficeTaxFunc()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./office-tax-func.component.ts">
</script>
