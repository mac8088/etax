<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="profileSetup">
                <h2 class="jh-entity-heading"><span v-text="$t('etaxApp.profileSetup.detail.title')">ProfileSetup</span> {{profileSetup.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('etaxApp.profileSetup.profileCode')">Profile Code</span>
                    </dt>
                    <dd>
                        <span>{{profileSetup.profileCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.profileSetup.cstdOfficeType')">Cstd Office Type</span>
                    </dt>
                    <dd>
                        <span>{{profileSetup.cstdOfficeType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.profileSetup.cstdUserType')">Cstd User Type</span>
                    </dt>
                    <dd>
                        <span>{{profileSetup.cstdUserType}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="profileSetup.id" :to="{name: 'ProfileSetupEdit', params: {profileSetupId: profileSetup.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./profile-setup-details.component.ts">
</script>
