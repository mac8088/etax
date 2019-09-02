<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="office">
                <h2 class="jh-entity-heading"><span v-text="$t('etaxApp.office.detail.title')">Office</span> {{office.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('etaxApp.office.cstdOfficeType')">Cstd Office Type</span>
                    </dt>
                    <dd>
                        <span>{{office.cstdOfficeType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.office.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{office.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.office.cstdClassifierCode')">Cstd Classifier Code</span>
                    </dt>
                    <dd>
                        <span>{{office.cstdClassifierCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.office.effectiveDate')">Effective Date</span>
                    </dt>
                    <dd>
                        <span>{{office.effectiveDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.office.expiryDate')">Expiry Date</span>
                    </dt>
                    <dd>
                        <span>{{office.expiryDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.office.phone')">Phone</span>
                    </dt>
                    <dd>
                        <span>{{office.phone}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.office.fax')">Fax</span>
                    </dt>
                    <dd>
                        <span>{{office.fax}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.office.stl')">Stl</span>
                    </dt>
                    <dd>
                        <span>{{office.stl}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.office.mngOffice')">Mng Office</span>
                    </dt>
                    <dd>
                        <span>{{office.mngOffice}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.office.physicalAdr')">Physical Adr</span>
                    </dt>
                    <dd>
                        <span>{{office.physicalAdr}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.office.postalAadr')">Postal Aadr</span>
                    </dt>
                    <dd>
                        <span>{{office.postalAadr}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.office.pinCode')">Pin Code</span>
                    </dt>
                    <dd>
                        <span>{{office.pinCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.office.cstdWeekWorkingDay')">Cstd Week Working Day</span>
                    </dt>
                    <dd>
                        <span>{{office.cstdWeekWorkingDay}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.office.officeCode')">Office Code</span>
                    </dt>
                    <dd>
                        <span>{{office.officeCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.office.cstdOfficeSubType')">Cstd Office Sub Type</span>
                    </dt>
                    <dd>
                        <span>{{office.cstdOfficeSubType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.office.cstdOfficeFuncType')">Cstd Office Func Type</span>
                    </dt>
                    <dd>
                        <span>{{office.cstdOfficeFuncType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.office.ccVersion')">Cc Version</span>
                    </dt>
                    <dd>
                        <span>{{office.ccVersion}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="office.id" :to="{name: 'OfficeEdit', params: {officeId: office.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./office-details.component.ts">
</script>
