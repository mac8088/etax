<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('etaxApp.stdCodesProp.home.title')" id="std-codes-prop-heading">Std Codes Props</span>
            <router-link :to="{name: 'StdCodesPropCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-std-codes-prop">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('etaxApp.stdCodesProp.home.createLabel')">
                    Create new StdCodesProp
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
        <div class="alert alert-warning" v-if="!isFetching && stdCodesProps && stdCodesProps.length === 0">
            <span v-text="$t('etaxApp.stdCodesProp.home.notFound')">No stdCodesProps found</span>
        </div>
        <div class="table-responsive" v-if="stdCodesProps && stdCodesProps.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesProp.groupCode')">Group Code</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesProp.internalCode')">Internal Code</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesProp.propCode')">Prop Code</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesProp.startDate')">Start Date</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesProp.endDate')">End Date</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesProp.valueDate')">Value Date</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesProp.valueString')">Value String</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesProp.valueBool')">Value Bool</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesProp.valueNumber')">Value Number</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="stdCodesProp in stdCodesProps"
                    :key="stdCodesProp.id">
                    <td>
                        <router-link :to="{name: 'StdCodesPropView', params: {stdCodesPropId: stdCodesProp.id}}">{{stdCodesProp.id}}</router-link>
                    </td>
                    <td>{{stdCodesProp.groupCode}}</td>
                    <td>{{stdCodesProp.internalCode}}</td>
                    <td>{{stdCodesProp.propCode}}</td>
                    <td>{{stdCodesProp.startDate | formatDate}}</td>
                    <td>{{stdCodesProp.endDate | formatDate}}</td>
                    <td>{{stdCodesProp.valueDate}}</td>
                    <td>{{stdCodesProp.valueString}}</td>
                    <td>{{stdCodesProp.valueBool}}</td>
                    <td>{{stdCodesProp.valueNumber}}</td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'StdCodesPropView', params: {stdCodesPropId: stdCodesProp.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'StdCodesPropEdit', params: {stdCodesPropId: stdCodesProp.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(stdCodesProp)"
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
            <span slot="modal-title"><span id="etaxApp.stdCodesProp.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-stdCodesProp-heading" v-bind:title="$t('etaxApp.stdCodesProp.delete.question')">Are you sure you want to delete this Std Codes Prop?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-stdCodesProp" v-text="$t('entity.action.delete')" v-on:click="removeStdCodesProp()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./std-codes-prop.component.ts">
</script>
