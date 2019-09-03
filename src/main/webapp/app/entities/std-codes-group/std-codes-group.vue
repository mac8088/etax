<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('etaxApp.stdCodesGroup.home.title')" id="std-codes-group-heading">Std Codes Groups</span>
            <router-link :to="{name: 'StdCodesGroupCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-std-codes-group">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('etaxApp.stdCodesGroup.home.createLabel')">
                    Create new StdCodesGroup
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
        <div class="alert alert-warning" v-if="!isFetching && stdCodesGroups && stdCodesGroups.length === 0">
            <span v-text="$t('etaxApp.stdCodesGroup.home.notFound')">No stdCodesGroups found</span>
        </div>
        <div class="table-responsive" v-if="stdCodesGroups && stdCodesGroups.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroup.groupCode')">Group Code</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroup.groupDesc')">Group Desc</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroup.systemInd')">System Ind</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroup.secLevelRequired')">Sec Level Required</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroup.valueRequired')">Value Required</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroup.valueType')">Value Type</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroup.descriptionRequired')">Description Required</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroup.externalCodeRequired')">External Code Required</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroup.externalCodeLength')">External Code Length</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroup.parentGroupRequired')">Parent Group Required</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroup.parentGroupCode')">Parent Group Code</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroup.touppercase')">Touppercase</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="stdCodesGroup in stdCodesGroups"
                    :key="stdCodesGroup.id">
                    <td>
                        <router-link :to="{name: 'StdCodesGroupView', params: {stdCodesGroupId: stdCodesGroup.id}}">{{stdCodesGroup.id}}</router-link>
                    </td>
                    <td>{{stdCodesGroup.groupCode}}</td>
                    <td>{{stdCodesGroup.groupDesc}}</td>
                    <td v-text="$t('etaxApp.OptionIndicator.' + stdCodesGroup.systemInd)">{{stdCodesGroup.systemInd}}</td>
                    <td v-text="$t('etaxApp.OptionIndicator.' + stdCodesGroup.secLevelRequired)">{{stdCodesGroup.secLevelRequired}}</td>
                    <td v-text="$t('etaxApp.OptionIndicator.' + stdCodesGroup.valueRequired)">{{stdCodesGroup.valueRequired}}</td>
                    <td v-text="$t('etaxApp.ValueTypeIndicator.' + stdCodesGroup.valueType)">{{stdCodesGroup.valueType}}</td>
                    <td v-text="$t('etaxApp.OptionIndicator.' + stdCodesGroup.descriptionRequired)">{{stdCodesGroup.descriptionRequired}}</td>
                    <td v-text="$t('etaxApp.OptionIndicator.' + stdCodesGroup.externalCodeRequired)">{{stdCodesGroup.externalCodeRequired}}</td>
                    <td>{{stdCodesGroup.externalCodeLength}}</td>
                    <td v-text="$t('etaxApp.OptionIndicator.' + stdCodesGroup.parentGroupRequired)">{{stdCodesGroup.parentGroupRequired}}</td>
                    <td>{{stdCodesGroup.parentGroupCode}}</td>
                    <td>{{stdCodesGroup.touppercase}}</td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'StdCodesGroupView', params: {stdCodesGroupId: stdCodesGroup.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'StdCodesGroupEdit', params: {stdCodesGroupId: stdCodesGroup.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(stdCodesGroup)"
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
            <span slot="modal-title"><span id="etaxApp.stdCodesGroup.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-stdCodesGroup-heading" v-bind:title="$t('etaxApp.stdCodesGroup.delete.question')">Are you sure you want to delete this Std Codes Group?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-stdCodesGroup" v-text="$t('entity.action.delete')" v-on:click="removeStdCodesGroup()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./std-codes-group.component.ts">
</script>
