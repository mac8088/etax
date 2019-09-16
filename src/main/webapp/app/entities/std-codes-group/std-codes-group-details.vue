<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="stdCodesGroup">
                <h2 class="jh-entity-heading"><span v-text="$t('etaxApp.stdCodesGroup.detail.title')">StdCodesGroup</span> {{stdCodesGroup.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroup.groupCode')">Group Code</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesGroup.groupCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroup.groupDesc')">Group Desc</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesGroup.groupDesc}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroup.systemInd')">System Ind</span>
                    </dt>
                    <dd>
                        <span v-text="$t('etaxApp.OptionIndicator.' + stdCodesGroup.systemInd)">{{stdCodesGroup.systemInd}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroup.secLevelRequired')">Sec Level Required</span>
                    </dt>
                    <dd>
                        <span v-text="$t('etaxApp.OptionIndicator.' + stdCodesGroup.secLevelRequired)">{{stdCodesGroup.secLevelRequired}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroup.valueRequired')">Value Required</span>
                    </dt>
                    <dd>
                        <span v-text="$t('etaxApp.OptionIndicator.' + stdCodesGroup.valueRequired)">{{stdCodesGroup.valueRequired}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroup.valueType')">Value Type</span>
                    </dt>
                    <dd>
                        <span v-text="$t('etaxApp.ValueTypeIndicator.' + stdCodesGroup.valueType)">{{stdCodesGroup.valueType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroup.descriptionRequired')">Description Required</span>
                    </dt>
                    <dd>
                        <span v-text="$t('etaxApp.OptionIndicator.' + stdCodesGroup.descriptionRequired)">{{stdCodesGroup.descriptionRequired}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroup.externalCodeRequired')">External Code Required</span>
                    </dt>
                    <dd>
                        <span v-text="$t('etaxApp.OptionIndicator.' + stdCodesGroup.externalCodeRequired)">{{stdCodesGroup.externalCodeRequired}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroup.externalCodeLength')">External Code Length</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesGroup.externalCodeLength}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroup.parentGroupRequired')">Parent Group Required</span>
                    </dt>
                    <dd>
                        <span v-text="$t('etaxApp.OptionIndicator.' + stdCodesGroup.parentGroupRequired)">{{stdCodesGroup.parentGroupRequired}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroup.parentGroupCode')">Parent Group Code</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesGroup.parentGroupCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroup.touppercase')">Touppercase</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesGroup.touppercase}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="stdCodesGroup.id" :to="{name: 'StdCodesGroupEdit', params: {stdCodesGroupId: stdCodesGroup.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./std-codes-group-details.component.ts">
</script>
