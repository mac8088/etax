<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="officeAreaCode">
                <h2 class="jh-entity-heading"><span v-text="$t('etaxApp.officeAreaCode.detail.title')">OfficeAreaCode</span> {{officeAreaCode.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('etaxApp.officeAreaCode.officeId')">Office Id</span>
                    </dt>
                    <dd>
                        <span>{{officeAreaCode.officeId}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.officeAreaCode.fromPin')">From Pin</span>
                    </dt>
                    <dd>
                        <span>{{officeAreaCode.fromPin}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.officeAreaCode.toPin')">To Pin</span>
                    </dt>
                    <dd>
                        <span>{{officeAreaCode.toPin}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="officeAreaCode.id" :to="{name: 'OfficeAreaCodeEdit', params: {officeAreaCodeId: officeAreaCode.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./office-area-code-details.component.ts">
</script>
