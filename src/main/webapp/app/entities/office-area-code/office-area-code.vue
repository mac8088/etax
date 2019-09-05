<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('etaxApp.officeAreaCode.home.title')" id="office-area-code-heading">Office Area Codes</span>
            <router-link :to="{name: 'OfficeAreaCodeCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-office-area-code">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('etaxApp.officeAreaCode.home.createLabel')">
                    Create new OfficeAreaCode
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
        <div class="alert alert-warning" v-if="!isFetching && officeAreaCodes && officeAreaCodes.length === 0">
            <span v-text="$t('etaxApp.officeAreaCode.home.notFound')">No officeAreaCodes found</span>
        </div>
        <div class="table-responsive" v-if="officeAreaCodes && officeAreaCodes.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('officeId')"><span v-text="$t('etaxApp.officeAreaCode.officeId')">Office Id</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fromPin')"><span v-text="$t('etaxApp.officeAreaCode.fromPin')">From Pin</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('toPin')"><span v-text="$t('etaxApp.officeAreaCode.toPin')">To Pin</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="officeAreaCode of orderBy(officeAreaCodes, propOrder, reverse === true ? 1 : -1)"
                    :key="officeAreaCode.id">
                    <td>
                        <router-link :to="{name: 'OfficeAreaCodeView', params: {officeAreaCodeId: officeAreaCode.id}}">{{officeAreaCode.id}}</router-link>
                    </td>
                    <td>{{officeAreaCode.officeId}}</td>
                    <td>{{officeAreaCode.fromPin}}</td>
                    <td>{{officeAreaCode.toPin}}</td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'OfficeAreaCodeView', params: {officeAreaCodeId: officeAreaCode.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'OfficeAreaCodeEdit', params: {officeAreaCodeId: officeAreaCode.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(officeAreaCode)"
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
            <span slot="modal-title"><span id="etaxApp.officeAreaCode.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-officeAreaCode-heading" v-bind:title="$t('etaxApp.officeAreaCode.delete.question')">Are you sure you want to delete this Office Area Code?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-officeAreaCode" v-text="$t('entity.action.delete')" v-on:click="removeOfficeAreaCode()">Delete</button>
            </div>
        </b-modal>
        <!-- Pager is not implemented yet, so this is normal pagination instead -->
        <div v-if="officeAreaCodes && officeAreaCodes.length">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./office-area-code.component.ts">
</script>
