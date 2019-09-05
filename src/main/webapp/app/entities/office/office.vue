<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('etaxApp.office.home.title')" id="office-heading">Offices</span>
            <router-link :to="{name: 'OfficeCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-office">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('etaxApp.office.home.createLabel')">
                    Create new Office
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
        <div class="alert alert-warning" v-if="!isFetching && offices && offices.length === 0">
            <span v-text="$t('etaxApp.office.home.notFound')">No offices found</span>
        </div>
        <div class="table-responsive" v-if="offices && offices.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cstdOfficeType')"><span v-text="$t('etaxApp.office.cstdOfficeType')">Cstd Office Type</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('etaxApp.office.name')">Name</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cstdClassifierCode')"><span v-text="$t('etaxApp.office.cstdClassifierCode')">Cstd Classifier Code</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('effectiveDate')"><span v-text="$t('etaxApp.office.effectiveDate')">Effective Date</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('expiryDate')"><span v-text="$t('etaxApp.office.expiryDate')">Expiry Date</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('phone')"><span v-text="$t('etaxApp.office.phone')">Phone</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fax')"><span v-text="$t('etaxApp.office.fax')">Fax</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('stl')"><span v-text="$t('etaxApp.office.stl')">Stl</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('mngOffice')"><span v-text="$t('etaxApp.office.mngOffice')">Mng Office</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('physicalAdr')"><span v-text="$t('etaxApp.office.physicalAdr')">Physical Adr</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('postalAadr')"><span v-text="$t('etaxApp.office.postalAadr')">Postal Aadr</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('pinCode')"><span v-text="$t('etaxApp.office.pinCode')">Pin Code</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cstdWeekWorkingDay')"><span v-text="$t('etaxApp.office.cstdWeekWorkingDay')">Cstd Week Working Day</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('officeCode')"><span v-text="$t('etaxApp.office.officeCode')">Office Code</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cstdOfficeSubType')"><span v-text="$t('etaxApp.office.cstdOfficeSubType')">Cstd Office Sub Type</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cstdOfficeFuncType')"><span v-text="$t('etaxApp.office.cstdOfficeFuncType')">Cstd Office Func Type</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('ccVersion')"><span v-text="$t('etaxApp.office.ccVersion')">Cc Version</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="office of orderBy(offices, propOrder, reverse === true ? 1 : -1)"
                    :key="office.id">
                    <td>
                        <router-link :to="{name: 'OfficeView', params: {officeId: office.id}}">{{office.id}}</router-link>
                    </td>
                    <td>{{office.cstdOfficeType}}</td>
                    <td>{{office.name}}</td>
                    <td>{{office.cstdClassifierCode}}</td>
                    <td>{{office.effectiveDate | formatDate}}</td>
                    <td>{{office.expiryDate | formatDate}}</td>
                    <td>{{office.phone}}</td>
                    <td>{{office.fax}}</td>
                    <td>{{office.stl}}</td>
                    <td>{{office.mngOffice}}</td>
                    <td>{{office.physicalAdr}}</td>
                    <td>{{office.postalAadr}}</td>
                    <td>{{office.pinCode}}</td>
                    <td>{{office.cstdWeekWorkingDay}}</td>
                    <td>{{office.officeCode}}</td>
                    <td>{{office.cstdOfficeSubType}}</td>
                    <td>{{office.cstdOfficeFuncType}}</td>
                    <td>{{office.ccVersion}}</td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'OfficeView', params: {officeId: office.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'OfficeEdit', params: {officeId: office.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(office)"
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
            <span slot="modal-title"><span id="etaxApp.office.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-office-heading" v-bind:title="$t('etaxApp.office.delete.question')">Are you sure you want to delete this Office?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-office" v-text="$t('entity.action.delete')" v-on:click="removeOffice()">Delete</button>
            </div>
        </b-modal>
        <div v-if="offices && offices.length">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./office.component.ts">
</script>
