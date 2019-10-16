<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('etaxApp.uiapp.home.title')" id="uiapp-heading">Uiapps</span>
            <router-link :to="{name: 'UiappCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-uiapp">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('etaxApp.uiapp.home.createLabel')">
                    Create new Uiapp
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
        <div class="alert alert-warning" v-if="!isFetching && uiapps && uiapps.length === 0">
            <span v-text="$t('etaxApp.uiapp.home.notFound')">No uiapps found</span>
        </div>
        <div class="table-responsive" v-if="uiapps && uiapps.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('appCode')"><span v-text="$t('etaxApp.uiapp.appCode')">App Code</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('appName')"><span v-text="$t('etaxApp.uiapp.appName')">App Name</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('appDesc')"><span v-text="$t('etaxApp.uiapp.appDesc')">App Desc</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cstdModule')"><span v-text="$t('etaxApp.uiapp.cstdModule')">Cstd Module</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('appMessage')"><span v-text="$t('etaxApp.uiapp.appMessage')">App Message</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="uiapp of orderBy(uiapps, propOrder, reverse === true ? 1 : -1)"
                    :key="uiapp.id">
                    <td>
                        <router-link :to="{name: 'UiappView', params: {uiappId: uiapp.id}}">{{uiapp.id}}</router-link>
                    </td>
                    <td>{{uiapp.appCode}}</td>
                    <td>{{uiapp.appName}}</td>
                    <td>{{uiapp.appDesc}}</td>
                    <td>{{uiapp.cstdModule}}</td>
                    <td>{{uiapp.appMessage}}</td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'UiappView', params: {uiappId: uiapp.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'UiappEdit', params: {uiappId: uiapp.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(uiapp)"
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
            <span slot="modal-title"><span id="etaxApp.uiapp.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-uiapp-heading" v-bind:title="$t('etaxApp.uiapp.delete.question')">Are you sure you want to delete this Uiapp?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-uiapp" v-text="$t('entity.action.delete')" v-on:click="removeUiapp()">Delete</button>
            </div>
        </b-modal>
        <div v-if="uiapps && uiapps.length">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./uiapp.component.ts">
</script>
