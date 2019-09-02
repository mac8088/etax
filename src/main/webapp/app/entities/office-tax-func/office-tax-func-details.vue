<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="officeTaxFunc">
                <h2 class="jh-entity-heading"><span v-text="$t('etaxApp.officeTaxFunc.detail.title')">OfficeTaxFunc</span> {{officeTaxFunc.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('etaxApp.officeTaxFunc.taxOfficeId')">Tax Office Id</span>
                    </dt>
                    <dd>
                        <span>{{officeTaxFunc.taxOfficeId}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.officeTaxFunc.funcOfficeId')">Func Office Id</span>
                    </dt>
                    <dd>
                        <span>{{officeTaxFunc.funcOfficeId}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.officeTaxFunc.startDate')">Start Date</span>
                    </dt>
                    <dd>
                        <span>{{officeTaxFunc.startDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.officeTaxFunc.endDate')">End Date</span>
                    </dt>
                    <dd>
                        <span>{{officeTaxFunc.endDate}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="officeTaxFunc.id" :to="{name: 'OfficeTaxFuncEdit', params: {officeTaxFuncId: officeTaxFunc.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./office-tax-func-details.component.ts">
</script>
