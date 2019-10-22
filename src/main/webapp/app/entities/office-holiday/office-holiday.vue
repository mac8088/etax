<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('etaxApp.officeHoliday.home.title')" id="office-holiday-heading">Office Holidays</span>
            <router-link :to="{name: 'OfficeHolidayCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-office-holiday">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('etaxApp.officeHoliday.home.createLabel')">
                    Create new OfficeHoliday
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
        <div class="alert alert-warning" v-if="!isFetching && officeHolidays && officeHolidays.length === 0">
            <span v-text="$t('etaxApp.officeHoliday.home.notFound')">No officeHolidays found</span>
        </div>
        <div class="table-responsive" v-if="officeHolidays && officeHolidays.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('officeId')"><span v-text="$t('etaxApp.officeHoliday.officeId')">Office Id</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('holidayId')"><span v-text="$t('etaxApp.officeHoliday.holidayId')">Holiday Id</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="officeHoliday of orderBy(officeHolidays, propOrder, reverse === true ? 1 : -1)"
                    :key="officeHoliday.id">
                    <td>
                        <router-link :to="{name: 'OfficeHolidayView', params: {officeHolidayId: officeHoliday.id}}">{{officeHoliday.id}}</router-link>
                    </td>
                    <td>{{officeHoliday.officeId}}</td>
                    <td>{{officeHoliday.holidayId}}</td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'OfficeHolidayView', params: {officeHolidayId: officeHoliday.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'OfficeHolidayEdit', params: {officeHolidayId: officeHoliday.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(officeHoliday)"
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
            <span slot="modal-title"><span id="etaxApp.officeHoliday.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-officeHoliday-heading" v-bind:title="$t('etaxApp.officeHoliday.delete.question')">Are you sure you want to delete this Office Holiday?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-officeHoliday" v-text="$t('entity.action.delete')" v-on:click="removeOfficeHoliday()">Delete</button>
            </div>
        </b-modal>
        <!-- Pager is not implemented yet, so this is normal pagination instead -->
        <div v-if="officeHolidays && officeHolidays.length">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./office-holiday.component.ts">
</script>
