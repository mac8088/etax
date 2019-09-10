<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="stdCodesGroupProp">
                <h2 class="jh-entity-heading"><span v-text="$t('etaxApp.stdCodesGroupProp.detail.title')">StdCodesGroupProp</span> {{stdCodesGroupProp.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroupProp.groupCode')">Group Code</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesGroupProp.groupCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroupProp.propCode')">Prop Code</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesGroupProp.propCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroupProp.propDesc')">Prop Desc</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesGroupProp.propDesc}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroupProp.startDate')">Start Date</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesGroupProp.startDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroupProp.endDate')">End Date</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesGroupProp.endDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroupProp.propType')">Prop Type</span>
                    </dt>
                    <dd>
                        <span v-text="$t('etaxApp.ValueTypeIndicator.' + stdCodesGroupProp.propType)">{{stdCodesGroupProp.propType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroupProp.propMdtr')">Prop Mdtr</span>
                    </dt>
                    <dd>
                        <span v-text="$t('etaxApp.OptionIndicator.' + stdCodesGroupProp.propMdtr)">{{stdCodesGroupProp.propMdtr}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroupProp.dfltValueDate')">Dflt Value Date</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesGroupProp.dfltValueDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroupProp.dfltValueString')">Dflt Value String</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesGroupProp.dfltValueString}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroupProp.dfltValueBool')">Dflt Value Bool</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesGroupProp.dfltValueBool}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.stdCodesGroupProp.dfltValueNumber')">Dflt Value Number</span>
                    </dt>
                    <dd>
                        <span>{{stdCodesGroupProp.dfltValueNumber}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="stdCodesGroupProp.id" :to="{name: 'StdCodesGroupPropEdit', params: {stdCodesGroupPropId: stdCodesGroupProp.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./std-codes-group-prop-details.component.ts">
</script>
