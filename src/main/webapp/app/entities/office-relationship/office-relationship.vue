<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('etaxApp.officeRelationship.home.title')" id="office-relationship-heading">Office Relationships</span>
            <router-link :to="{name: 'OfficeRelationshipCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-office-relationship">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('etaxApp.officeRelationship.home.createLabel')">
                    Create new OfficeRelationship
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
        <div class="alert alert-warning" v-if="!isFetching && officeRelationships && officeRelationships.length === 0">
            <span v-text="$t('etaxApp.officeRelationship.home.notFound')">No officeRelationships found</span>
        </div>
        <div class="table-responsive" v-if="officeRelationships && officeRelationships.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('parentId')"><span v-text="$t('etaxApp.officeRelationship.parentId')">Parent Id</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('chileId')"><span v-text="$t('etaxApp.officeRelationship.chileId')">Chile Id</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('startDate')"><span v-text="$t('etaxApp.officeRelationship.startDate')">Start Date</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('endDate')"><span v-text="$t('etaxApp.officeRelationship.endDate')">End Date</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('ccVersion')"><span v-text="$t('etaxApp.officeRelationship.ccVersion')">Cc Version</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="officeRelationship of orderBy(officeRelationships, propOrder, reverse === true ? 1 : -1)"
                    :key="officeRelationship.id">
                    <td>
                        <router-link :to="{name: 'OfficeRelationshipView', params: {officeRelationshipId: officeRelationship.id}}">{{officeRelationship.id}}</router-link>
                    </td>
                    <td>{{officeRelationship.parentId}}</td>
                    <td>{{officeRelationship.chileId}}</td>
                    <td>{{officeRelationship.startDate | formatDate}}</td>
                    <td>{{officeRelationship.endDate | formatDate}}</td>
                    <td>{{officeRelationship.ccVersion}}</td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'OfficeRelationshipView', params: {officeRelationshipId: officeRelationship.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'OfficeRelationshipEdit', params: {officeRelationshipId: officeRelationship.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(officeRelationship)"
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
            <span slot="modal-title"><span id="etaxApp.officeRelationship.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-officeRelationship-heading" v-bind:title="$t('etaxApp.officeRelationship.delete.question')">Are you sure you want to delete this Office Relationship?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-officeRelationship" v-text="$t('entity.action.delete')" v-on:click="removeOfficeRelationship()">Delete</button>
            </div>
        </b-modal>
        <!-- Pager is not implemented yet, so this is normal pagination instead -->
        <div v-if="officeRelationships && officeRelationships.length">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./office-relationship.component.ts">
</script>
