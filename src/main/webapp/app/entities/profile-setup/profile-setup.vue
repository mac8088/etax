<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('etaxApp.profileSetup.home.title')" id="profile-setup-heading">Profile Setups</span>
            <router-link :to="{name: 'ProfileSetupCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-profile-setup">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('etaxApp.profileSetup.home.createLabel')">
                    Create new ProfileSetup
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
        <div class="alert alert-warning" v-if="!isFetching && profileSetups && profileSetups.length === 0">
            <span v-text="$t('etaxApp.profileSetup.home.notFound')">No profileSetups found</span>
        </div>
        <div class="table-responsive" v-if="profileSetups && profileSetups.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('profileCode')"><span v-text="$t('etaxApp.profileSetup.profileCode')">Profile Code</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cstdOfficeType')"><span v-text="$t('etaxApp.profileSetup.cstdOfficeType')">Cstd Office Type</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cstdUserType')"><span v-text="$t('etaxApp.profileSetup.cstdUserType')">Cstd User Type</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="profileSetup of orderBy(profileSetups, propOrder, reverse === true ? 1 : -1)"
                    :key="profileSetup.id">
                    <td>
                        <router-link :to="{name: 'ProfileSetupView', params: {profileSetupId: profileSetup.id}}">{{profileSetup.id}}</router-link>
                    </td>
                    <td>{{profileSetup.profileCode}}</td>
                    <td>{{profileSetup.cstdOfficeType}}</td>
                    <td>{{profileSetup.cstdUserType}}</td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'ProfileSetupView', params: {profileSetupId: profileSetup.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ProfileSetupEdit', params: {profileSetupId: profileSetup.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(profileSetup)"
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
            <span slot="modal-title"><span id="etaxApp.profileSetup.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-profileSetup-heading" v-bind:title="$t('etaxApp.profileSetup.delete.question')">Are you sure you want to delete this Profile Setup?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-profileSetup" v-text="$t('entity.action.delete')" v-on:click="removeProfileSetup()">Delete</button>
            </div>
        </b-modal>
        <!-- Pager is not implemented yet, so this is normal pagination instead -->
        <div v-if="profileSetups && profileSetups.length">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./profile-setup.component.ts">
</script>
