<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="stdCodesProp">
                <h2 class="jh-entity-heading"><span v-text="$t('etaxApp.stdCodesProp.detail.title')">StdCodesProp</span> {{stdCodesProp.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesProp.groupCode')">Group Code</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesProp.groupCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesProp.internalCode')">Internal Code</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesProp.internalCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesProp.propCode')">Prop Code</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesProp.propCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesProp.startDate')">Start Date</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesProp.startDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesProp.endDate')">End Date</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesProp.endDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesProp.valueDate')">Value Date</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesProp.valueDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesProp.valueString')">Value String</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesProp.valueString}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesProp.valueBool')">Value Bool</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesProp.valueBool}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesProp.valueNumber')">Value Number</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesProp.valueNumber}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="stdCodesProp.id" :to="{name: 'StdCodesPropEdit', params: {stdCodesPropId: stdCodesProp.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./std-codes-prop-details.component.ts">
</script>
