<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="resource">
                <h2 class="jh-entity-heading"><span v-text="$t('etaxApp.resource.detail.title')">Resource</span> {{resource.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('etaxApp.resource.resCode')">Res Code</span>
                    </dt>
                    <dd>
                        <span>{{resource.resCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.resource.resName')">Res Name</span>
                    </dt>
                    <dd>
                        <span>{{resource.resName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.resource.resType')">Res Type</span>
                    </dt>
                    <dd>
                        <span v-text="$t('etaxApp.ResourceType.' + resource.resType)">{{resource.resType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.resource.appDesc')">App Desc</span>
                    </dt>
                    <dd>
                        <span>{{resource.appDesc}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.resource.cstdModule')">Cstd Module</span>
                    </dt>
                    <dd>
                        <span>{{resource.cstdModule}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.resource.resContent')">Res Content</span>
                    </dt>
                    <dd>
                        <span>{{resource.resContent}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="resource.id" :to="{name: 'ResourceEdit', params: {resourceId: resource.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./resource-details.component.ts">
</script>
