<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="publicHoliday">
                <h2 class="jh-entity-heading"><span v-text="$t('etaxApp.publicHoliday.detail.title')">PublicHoliday</span> {{publicHoliday.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('etaxApp.publicHoliday.cstdHolidayTypes')">Cstd Holiday Types</span>
                    </dt>
                    <dd>
                        <span>{{publicHoliday.cstdHolidayTypes}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.publicHoliday.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{publicHoliday.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.publicHoliday.date')">Date</span>
                    </dt>
                    <dd>
                        <span>{{publicHoliday.date}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.publicHoliday.workingFlag')">Working Flag</span>
                    </dt>
                    <dd>
                        <span>{{publicHoliday.workingFlag}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.publicHoliday.countryWide')">Country Wide</span>
                    </dt>
                    <dd>
                        <span>{{publicHoliday.countryWide}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.publicHoliday.ccVersion')">Cc Version</span>
                    </dt>
                    <dd>
                        <span>{{publicHoliday.ccVersion}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="publicHoliday.id" :to="{name: 'PublicHolidayEdit', params: {publicHolidayId: publicHoliday.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./public-holiday-details.component.ts">
</script>
