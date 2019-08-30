<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('etaxApp.stdCodes.home.title')" id="std-codes-heading">Std Codes</span>
            <router-link :to="{name: 'StdCodesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-std-codes">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('etaxApp.stdCodes.home.createLabel')">
                    Create new StdCodes
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
        <div class="alert alert-warning" v-if="!isFetching && stdCodes && stdCodes.length === 0">
            <span v-text="$t('etaxApp.stdCodes.home.notFound')">No stdCodes found</span>
        </div>
        <div class="table-responsive" v-if="stdCodes && stdCodes.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('etaxApp.stdCodes.groupCode')">Group Code</span></th>
                    <th><span v-text="$t('etaxApp.stdCodes.internalCode')">Internal Code</span></th>
                    <th><span v-text="$t('etaxApp.stdCodes.startDate')">Start Date</span></th>
                    <th><span v-text="$t('etaxApp.stdCodes.endDate')">End Date</span></th>
                    <th><span v-text="$t('etaxApp.stdCodes.parentInternalCode')">Parent Internal Code</span></th>
                    <th><span v-text="$t('etaxApp.stdCodes.comments')">Comments</span></th>
                    <th><span v-text="$t('etaxApp.stdCodes.secLevel')">Sec Level</span></th>
                    <th><span v-text="$t('etaxApp.stdCodes.codeValueDate')">Code Value Date</span></th>
                    <th><span v-text="$t('etaxApp.stdCodes.codeValueString')">Code Value String</span></th>
                    <th><span v-text="$t('etaxApp.stdCodes.codeValueBool')">Code Value Bool</span></th>
                    <th><span v-text="$t('etaxApp.stdCodes.codeValueNumber')">Code Value Number</span></th>
                    <th><span v-text="$t('etaxApp.stdCodes.stdCodesGroup')">Std Codes Group</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="stdCodes in stdCodes"
                    :key="stdCodes.id">
                    <td>
                        <router-link :to="{name: 'StdCodesView', params: {stdCodesId: stdCodes.id}}">{{stdCodes.id}}</router-link>
                    </td>
                    <td>{{stdCodes.groupCode}}</td>
                    <td>{{stdCodes.internalCode}}</td>
                    <td>{{stdCodes.startDate | formatDate}}</td>
                    <td>{{stdCodes.endDate | formatDate}}</td>
                    <td>{{stdCodes.parentInternalCode}}</td>
                    <td>{{stdCodes.comments}}</td>
                    <td>{{stdCodes.secLevel}}</td>
                    <td>{{stdCodes.codeValueDate}}</td>
                    <td>{{stdCodes.codeValueString}}</td>
                    <td>{{stdCodes.codeValueBool}}</td>
                    <td>{{stdCodes.codeValueNumber}}</td>
                    <td>
                        <div v-if="stdCodes.stdCodesGroup">
                            <router-link :to="{name: 'StdCodesGroupView', params: {stdCodesGroupId: stdCodes.stdCodesGroup.id}}">{{stdCodes.stdCodesGroup.groupCode}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'StdCodesView', params: {stdCodesId: stdCodes.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'StdCodesEdit', params: {stdCodesId: stdCodes.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(stdCodes)"
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
            <span slot="modal-title"><span id="etaxApp.stdCodes.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-stdCodes-heading" v-bind:title="$t('etaxApp.stdCodes.delete.question')">Are you sure you want to delete this Std Codes?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-stdCodes" v-text="$t('entity.action.delete')" v-on:click="removeStdCodes()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./std-codes.component.ts">
</script>
