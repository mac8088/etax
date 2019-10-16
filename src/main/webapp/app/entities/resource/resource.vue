<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('etaxApp.resource.home.title')" id="resource-heading">Resources</span>
            <router-link :to="{name: 'ResourceCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-resource">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('etaxApp.resource.home.createLabel')">
                    Create new Resource
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
        <div class="alert alert-warning" v-if="!isFetching && resources && resources.length === 0">
            <span v-text="$t('etaxApp.resource.home.notFound')">No resources found</span>
        </div>
        <div class="table-responsive" v-if="resources && resources.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('resCode')"><span v-text="$t('etaxApp.resource.resCode')">Res Code</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('resName')"><span v-text="$t('etaxApp.resource.resName')">Res Name</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('resType')"><span v-text="$t('etaxApp.resource.resType')">Res Type</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('appDesc')"><span v-text="$t('etaxApp.resource.appDesc')">App Desc</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cstdModule')"><span v-text="$t('etaxApp.resource.cstdModule')">Cstd Module</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('resContent')"><span v-text="$t('etaxApp.resource.resContent')">Res Content</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="resource of orderBy(resources, propOrder, reverse === true ? 1 : -1)"
                    :key="resource.id">
                    <td>
                        <router-link :to="{name: 'ResourceView', params: {resourceId: resource.id}}">{{resource.id}}</router-link>
                    </td>
                    <td>{{resource.resCode}}</td>
                    <td>{{resource.resName}}</td>
                    <td v-text="$t('etaxApp.ResourceType.' + resource.resType)">{{resource.resType}}</td>
                    <td>{{resource.appDesc}}</td>
                    <td>{{resource.cstdModule}}</td>
                    <td>{{resource.resContent}}</td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'ResourceView', params: {resourceId: resource.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ResourceEdit', params: {resourceId: resource.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(resource)"
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
            <span slot="modal-title"><span id="etaxApp.resource.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-resource-heading" v-bind:title="$t('etaxApp.resource.delete.question')">Are you sure you want to delete this Resource?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-resource" v-text="$t('entity.action.delete')" v-on:click="removeResource()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./resource.component.ts">
</script>
