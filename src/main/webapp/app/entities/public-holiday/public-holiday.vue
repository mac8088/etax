<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('etaxApp.publicHoliday.home.title')" id="public-holiday-heading">Public Holidays</span>
            <router-link :to="{name: 'PublicHolidayCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-public-holiday">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('etaxApp.publicHoliday.home.createLabel')">
                    Create new PublicHoliday
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
        <div class="alert alert-warning" v-if="!isFetching && publicHolidays && publicHolidays.length === 0">
            <span v-text="$t('etaxApp.publicHoliday.home.notFound')">No publicHolidays found</span>
        </div>
        <div class="table-responsive" v-if="publicHolidays && publicHolidays.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cstdHolidayTypes')"><span v-text="$t('etaxApp.publicHoliday.cstdHolidayTypes')">Cstd Holiday Types</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('etaxApp.publicHoliday.description')">Description</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('date')"><span v-text="$t('etaxApp.publicHoliday.date')">Date</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('workingFlag')"><span v-text="$t('etaxApp.publicHoliday.workingFlag')">Working Flag</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('countryWide')"><span v-text="$t('etaxApp.publicHoliday.countryWide')">Country Wide</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('ccVersion')"><span v-text="$t('etaxApp.publicHoliday.ccVersion')">Cc Version</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="publicHoliday of orderBy(publicHolidays, propOrder, reverse === true ? 1 : -1)"
                    :key="publicHoliday.id">
                    <td>
                        <router-link :to="{name: 'PublicHolidayView', params: {publicHolidayId: publicHoliday.id}}">{{publicHoliday.id}}</router-link>
                    </td>
                    <td>{{publicHoliday.cstdHolidayTypes}}</td>
                    <td>{{publicHoliday.description}}</td>
                    <td>{{publicHoliday.date}}</td>
                    <td>{{publicHoliday.workingFlag}}</td>
                    <td>{{publicHoliday.countryWide}}</td>
                    <td>{{publicHoliday.ccVersion}}</td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'PublicHolidayView', params: {publicHolidayId: publicHoliday.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'PublicHolidayEdit', params: {publicHolidayId: publicHoliday.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(publicHoliday)"
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
            <span slot="modal-title"><span id="etaxApp.publicHoliday.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-publicHoliday-heading" v-bind:title="$t('etaxApp.publicHoliday.delete.question')">Are you sure you want to delete this Public Holiday?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-publicHoliday" v-text="$t('entity.action.delete')" v-on:click="removePublicHoliday()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./public-holiday.component.ts">
</script>
