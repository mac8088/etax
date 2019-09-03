<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('etaxApp.officeWeekday.home.title')" id="office-weekday-heading">Office Weekdays</span>
            <router-link :to="{name: 'OfficeWeekdayCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-office-weekday">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('etaxApp.officeWeekday.home.createLabel')">
                    Create new OfficeWeekday
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
        <div class="alert alert-warning" v-if="!isFetching && officeWeekdays && officeWeekdays.length === 0">
            <span v-text="$t('etaxApp.officeWeekday.home.notFound')">No officeWeekdays found</span>
        </div>
        <div class="table-responsive" v-if="officeWeekdays && officeWeekdays.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('etaxApp.officeWeekday.officeId')">Office Id</span></th>
                    <th><span v-text="$t('etaxApp.officeWeekday.cstdWeekworkingDay')">Cstd Weekworking Day</span></th>
                    <th><span v-text="$t('etaxApp.officeWeekday.startDate')">Start Date</span></th>
                    <th><span v-text="$t('etaxApp.officeWeekday.endDate')">End Date</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="officeWeekday in officeWeekdays"
                    :key="officeWeekday.id">
                    <td>
                        <router-link :to="{name: 'OfficeWeekdayView', params: {officeWeekdayId: officeWeekday.id}}">{{officeWeekday.id}}</router-link>
                    </td>
                    <td>{{officeWeekday.officeId}}</td>
                    <td>{{officeWeekday.cstdWeekworkingDay}}</td>
                    <td>{{officeWeekday.startDate}}</td>
                    <td>{{officeWeekday.endDate}}</td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'OfficeWeekdayView', params: {officeWeekdayId: officeWeekday.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'OfficeWeekdayEdit', params: {officeWeekdayId: officeWeekday.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(officeWeekday)"
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
            <span slot="modal-title"><span id="etaxApp.officeWeekday.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-officeWeekday-heading" v-bind:title="$t('etaxApp.officeWeekday.delete.question')">Are you sure you want to delete this Office Weekday?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-officeWeekday" v-text="$t('entity.action.delete')" v-on:click="removeOfficeWeekday()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./office-weekday.component.ts">
</script>
