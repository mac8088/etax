<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="officeRelationship">
                <h2 class="jh-entity-heading"><span v-text="$t('etaxApp.officeRelationship.detail.title')">OfficeRelationship</span> {{officeRelationship.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('etaxApp.officeRelationship.parentId')">Parent Id</span>
                    </dt>
                    <dd>
                        <span>{{officeRelationship.parentId}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.officeRelationship.chileId')">Chile Id</span>
                    </dt>
                    <dd>
                        <span>{{officeRelationship.chileId}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.officeRelationship.startDate')">Start Date</span>
                    </dt>
                    <dd>
                        <span>{{officeRelationship.startDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.officeRelationship.endDate')">End Date</span>
                    </dt>
                    <dd>
                        <span>{{officeRelationship.endDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.officeRelationship.ccVersion')">Cc Version</span>
                    </dt>
                    <dd>
                        <span>{{officeRelationship.ccVersion}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="officeRelationship.id" :to="{name: 'OfficeRelationshipEdit', params: {officeRelationshipId: officeRelationship.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./office-relationship-details.component.ts">
</script>
