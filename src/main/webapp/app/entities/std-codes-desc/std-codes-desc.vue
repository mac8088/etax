<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('etaxApp.stdCodesDesc.home.title')" id="std-codes-desc-heading">Std Codes Descs</span>
            <router-link :to="{name: 'StdCodesDescCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-std-codes-desc">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('etaxApp.stdCodesDesc.home.createLabel')">
                    Create new StdCodesDesc
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
        <div class="alert alert-warning" v-if="!isFetching && stdCodesDescs && stdCodesDescs.length === 0">
            <span v-text="$t('etaxApp.stdCodesDesc.home.notFound')">No stdCodesDescs found</span>
        </div>
        <div class="table-responsive" v-if="stdCodesDescs && stdCodesDescs.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesDesc.groupCode')">Group Code</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesDesc.internalCode')">Internal Code</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesDesc.langCode')">Lang Code</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesDesc.startDate')">Start Date</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesDesc.endDate')">End Date</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesDesc.externalCode')">External Code</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesDesc.codeDesc')">Code Desc</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesDesc.stdCodes')">Std Codes</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="stdCodesDesc in stdCodesDescs"
                    :key="stdCodesDesc.id">
                    <td>
                        <router-link :to="{name: 'StdCodesDescView', params: {stdCodesDescId: stdCodesDesc.id}}">{{stdCodesDesc.id}}</router-link>
                    </td>
                    <td>{{stdCodesDesc.groupCode}}</td>
                    <td>{{stdCodesDesc.internalCode}}</td>
                    <td>{{stdCodesDesc.langCode}}</td>
                    <td>{{stdCodesDesc.startDate | formatDate}}</td>
                    <td>{{stdCodesDesc.endDate | formatDate}}</td>
                    <td>{{stdCodesDesc.externalCode}}</td>
                    <td>{{stdCodesDesc.codeDesc}}</td>
                    <td>
                        <div v-if="stdCodesDesc.stdCodes">
                            <router-link :to="{name: 'StdCodesView', params: {stdCodesId: stdCodesDesc.stdCodes.id}}">{{stdCodesDesc.stdCodes.internalCode}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'StdCodesDescView', params: {stdCodesDescId: stdCodesDesc.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'StdCodesDescEdit', params: {stdCodesDescId: stdCodesDesc.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(stdCodesDesc)"
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
            <span slot="modal-title"><span id="etaxApp.stdCodesDesc.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-stdCodesDesc-heading" v-bind:title="$t('etaxApp.stdCodesDesc.delete.question')">Are you sure you want to delete this Std Codes Desc?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-stdCodesDesc" v-text="$t('entity.action.delete')" v-on:click="removeStdCodesDesc()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./std-codes-desc.component.ts">
</script>
