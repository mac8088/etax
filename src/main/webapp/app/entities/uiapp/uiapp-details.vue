<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="uiapp">
                <h2 class="jh-entity-heading"><span v-text="$t('etaxApp.uiapp.detail.title')">Uiapp</span> {{uiapp.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('etaxApp.uiapp.appCode')">App Code</span>
                    </dt>
                    <dd>
                        <span>{{uiapp.appCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.uiapp.appName')">App Name</span>
                    </dt>
                    <dd>
                        <span>{{uiapp.appName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.uiapp.appDesc')">App Desc</span>
                    </dt>
                    <dd>
                        <span>{{uiapp.appDesc}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.uiapp.cstdModule')">Cstd Module</span>
                    </dt>
                    <dd>
                        <span>{{uiapp.cstdModule}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.uiapp.appMessage')">App Message</span>
                    </dt>
                    <dd>
                        <span>{{uiapp.appMessage}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.uiapp.resource')">Resource</span>
                    </dt>
                    <dd>
                        <span v-for="(resource, i) in uiapp.resources" :key="resource.id">{{i > 0 ? ', ' : ''}}
                            <router-link :to="{name: 'ResourceView', params: {resourceId: resource.id}}">{{resource.resCode}}</router-link>
                        </span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="uiapp.id" :to="{name: 'UiappEdit', params: {uiappId: uiapp.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./uiapp-details.component.ts">
</script>
