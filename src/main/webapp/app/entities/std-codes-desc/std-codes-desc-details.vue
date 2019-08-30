<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="stdCodesDesc">
                <h2 class="jh-entity-heading"><span v-text="$t('etaxApp.stdCodesDesc.detail.title')">StdCodesDesc</span> {{stdCodesDesc.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesDesc.groupCode')">Group Code</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesDesc.groupCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesDesc.internalCode')">Internal Code</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesDesc.internalCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesDesc.langCode')">Lang Code</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesDesc.langCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesDesc.startDate')">Start Date</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesDesc.startDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesDesc.endDate')">End Date</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesDesc.endDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesDesc.externalCode')">External Code</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesDesc.externalCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesDesc.codeDesc')">Code Desc</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesDesc.codeDesc}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesDesc.stdCodes')">Std Codes</span>
                    </dt>
                    <dd>
                        <div v-if="stdCodesDesc.stdCodes">
                            <router-link :to="{name: 'StdCodesView', params: {stdCodesId: stdCodesDesc.stdCodes.id}}">{{stdCodesDesc.stdCodes.internalCode}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="stdCodesDesc.id" :to="{name: 'StdCodesDescEdit', params: {stdCodesDescId: stdCodesDesc.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./std-codes-desc-details.component.ts">
</script>
