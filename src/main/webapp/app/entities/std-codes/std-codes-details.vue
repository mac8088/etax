<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="stdCodes">
                <h2 class="jh-entity-heading"><span v-text="$t('etaxApp.stdCodes.detail.title')">StdCodes</span> {{stdCodes.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('etaxApp.stdCodes.groupCode')">Group Code</span>
                    </dt>
                    <dd>
                        <span>{{stdCodes.groupCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodes.internalCode')">Internal Code</span>
                    </dt>
                    <dd>
                        <span>{{stdCodes.internalCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodes.startDate')">Start Date</span>
                    </dt>
                    <dd>
                        <span>{{stdCodes.startDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodes.endDate')">End Date</span>
                    </dt>
                    <dd>
                        <span>{{stdCodes.endDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodes.parentInternalCode')">Parent Internal Code</span>
                    </dt>
                    <dd>
                        <span>{{stdCodes.parentInternalCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodes.comments')">Comments</span>
                    </dt>
                    <dd>
                        <span>{{stdCodes.comments}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodes.secLevel')">Sec Level</span>
                    </dt>
                    <dd>
                        <span>{{stdCodes.secLevel}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodes.codeValueDate')">Code Value Date</span>
                    </dt>
                    <dd>
                        <span>{{stdCodes.codeValueDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodes.codeValueString')">Code Value String</span>
                    </dt>
                    <dd>
                        <span>{{stdCodes.codeValueString}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodes.codeValueBool')">Code Value Bool</span>
                    </dt>
                    <dd>
                        <span>{{stdCodes.codeValueBool}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodes.codeValueNumber')">Code Value Number</span>
                    </dt>
                    <dd>
                        <span>{{stdCodes.codeValueNumber}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodes.stdCodesGroup')">Std Codes Group</span>
                    </dt>
                    <dd>
                        <div v-if="stdCodes.stdCodesGroup">
                            <router-link :to="{name: 'StdCodesGroupView', params: {stdCodesGroupId: stdCodes.stdCodesGroup.id}}">{{stdCodes.stdCodesGroup.groupCode}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="stdCodes.id" :to="{name: 'StdCodesEdit', params: {stdCodesId: stdCodes.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./std-codes-details.component.ts">
</script>
