<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('etaxApp.stdCodesGroupProp.home.title')" id="std-codes-group-prop-heading">Std Codes Group Props</span>
            <router-link :to="{name: 'StdCodesGroupPropCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-std-codes-group-prop">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('etaxApp.stdCodesGroupProp.home.createLabel')">
                    Create new StdCodesGroupProp
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
        <div class="alert alert-warning" v-if="!isFetching && stdCodesGroupProps && stdCodesGroupProps.length === 0">
            <span v-text="$t('etaxApp.stdCodesGroupProp.home.notFound')">No stdCodesGroupProps found</span>
        </div>
        <div class="table-responsive" v-if="stdCodesGroupProps && stdCodesGroupProps.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroupProp.groupCode')">Group Code</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroupProp.propCode')">Prop Code</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroupProp.propDesc')">Prop Desc</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroupProp.startDate')">Start Date</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroupProp.endDate')">End Date</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroupProp.propType')">Prop Type</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroupProp.propMdtr')">Prop Mdtr</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroupProp.dfltValueDate')">Dflt Value Date</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroupProp.dfltValueString')">Dflt Value String</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroupProp.dfltValueBool')">Dflt Value Bool</span></th>
                    <th><span v-text="$t('etaxApp.stdCodesGroupProp.dfltValueNumber')">Dflt Value Number</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="stdCodesGroupProp in stdCodesGroupProps"
                    :key="stdCodesGroupProp.id">
                    <td>
                        <router-link :to="{name: 'StdCodesGroupPropView', params: {stdCodesGroupPropId: stdCodesGroupProp.id}}">{{stdCodesGroupProp.id}}</router-link>
                    </td>
                    <td>{{stdCodesGroupProp.groupCode}}</td>
                    <td>{{stdCodesGroupProp.propCode}}</td>
                    <td>{{stdCodesGroupProp.propDesc}}</td>
                    <td>{{stdCodesGroupProp.startDate | formatDate}}</td>
                    <td>{{stdCodesGroupProp.endDate | formatDate}}</td>
                    <td v-text="$t('etaxApp.ValueTypeIndicator.' + stdCodesGroupProp.propType)">{{stdCodesGroupProp.propType}}</td>
                    <td v-text="$t('etaxApp.OptionIndicator.' + stdCodesGroupProp.propMdtr)">{{stdCodesGroupProp.propMdtr}}</td>
                    <td>{{stdCodesGroupProp.dfltValueDate}}</td>
                    <td>{{stdCodesGroupProp.dfltValueString}}</td>
                    <td>{{stdCodesGroupProp.dfltValueBool}}</td>
                    <td>{{stdCodesGroupProp.dfltValueNumber}}</td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'StdCodesGroupPropView', params: {stdCodesGroupPropId: stdCodesGroupProp.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'StdCodesGroupPropEdit', params: {stdCodesGroupPropId: stdCodesGroupProp.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(stdCodesGroupProp)"
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
            <span slot="modal-title"><span id="etaxApp.stdCodesGroupProp.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-stdCodesGroupProp-heading" v-bind:title="$t('etaxApp.stdCodesGroupProp.delete.question')">Are you sure you want to delete this Std Codes Group Prop?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-stdCodesGroupProp" v-text="$t('entity.action.delete')" v-on:click="removeStdCodesGroupProp()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./std-codes-group-prop.component.ts">
</script>
