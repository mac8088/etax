<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="officeWeekday">
                <h2 class="jh-entity-heading"><span v-text="$t('etaxApp.officeWeekday.detail.title')">OfficeWeekday</span> {{officeWeekday.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('etaxApp.officeWeekday.officeId')">Office Id</span>
                    </dt>
                    <dd>
                        <span>{{officeWeekday.officeId}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.officeWeekday.cstdWeekworkingDay')">Cstd Weekworking Day</span>
                    </dt>
                    <dd>
                        <span>{{officeWeekday.cstdWeekworkingDay}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.officeWeekday.startDate')">Start Date</span>
                    </dt>
                    <dd>
                        <span>{{officeWeekday.startDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.officeWeekday.endDate')">End Date</span>
                    </dt>
                    <dd>
                        <span>{{officeWeekday.endDate}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="officeWeekday.id" :to="{name: 'OfficeWeekdayEdit', params: {officeWeekdayId: officeWeekday.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./office-weekday-details.component.ts">
</script>
