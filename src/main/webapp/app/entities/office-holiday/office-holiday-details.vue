<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="officeHoliday">
                <h2 class="jh-entity-heading"><span v-text="$t('etaxApp.officeHoliday.detail.title')">OfficeHoliday</span> {{officeHoliday.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('etaxApp.officeHoliday.officeId')">Office Id</span>
                    </dt>
                    <dd>
                        <span>{{officeHoliday.officeId}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.officeHoliday.holidayId')">Holiday Id</span>
                    </dt>
                    <dd>
                        <span>{{officeHoliday.holidayId}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="officeHoliday.id" :to="{name: 'OfficeHolidayEdit', params: {officeHolidayId: officeHoliday.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./office-holiday-details.component.ts">
</script>
