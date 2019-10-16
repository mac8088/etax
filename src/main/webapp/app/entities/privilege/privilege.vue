<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('etaxApp.privilege.home.title')" id="privilege-heading">Privileges</span>
            <router-link :to="{name: 'PrivilegeCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-privilege">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('etaxApp.privilege.home.createLabel')">
                    Create new Privilege
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
        <div class="alert alert-warning" v-if="!isFetching && privileges && privileges.length === 0">
            <span v-text="$t('etaxApp.privilege.home.notFound')">No privileges found</span>
        </div>
        <div class="table-responsive" v-if="privileges && privileges.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('appCode')"><span v-text="$t('etaxApp.privilege.appCode')">App Code</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('userName')"><span v-text="$t('etaxApp.privilege.userName')">User Name</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('profileName')"><span v-text="$t('etaxApp.privilege.profileName')">Profile Name</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('limit')"><span v-text="$t('etaxApp.privilege.limit')">Limit</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('confirmStatus')"><span v-text="$t('etaxApp.privilege.confirmStatus')">Confirm Status</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('effectiveDate')"><span v-text="$t('etaxApp.privilege.effectiveDate')">Effective Date</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('expiryDate')"><span v-text="$t('etaxApp.privilege.expiryDate')">Expiry Date</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="privilege of orderBy(privileges, propOrder, reverse === true ? 1 : -1)"
                    :key="privilege.id">
                    <td>
                        <router-link :to="{name: 'PrivilegeView', params: {privilegeId: privilege.id}}">{{privilege.id}}</router-link>
                    </td>
                    <td>{{privilege.appCode}}</td>
                    <td>{{privilege.userName}}</td>
                    <td>{{privilege.profileName}}</td>
                    <td>{{privilege.limit}}</td>
                    <td>{{privilege.confirmStatus}}</td>
                    <td>{{privilege.effectiveDate | formatDate}}</td>
                    <td>{{privilege.expiryDate | formatDate}}</td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'PrivilegeView', params: {privilegeId: privilege.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'PrivilegeEdit', params: {privilegeId: privilege.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(privilege)"
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
            <span slot="modal-title"><span id="etaxApp.privilege.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-privilege-heading" v-bind:title="$t('etaxApp.privilege.delete.question')">Are you sure you want to delete this Privilege?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-privilege" v-text="$t('entity.action.delete')" v-on:click="removePrivilege()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./privilege.component.ts">
</script>
