<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="privilege">
                <h2 class="jh-entity-heading"><span v-text="$t('etaxApp.privilege.detail.title')">Privilege</span> {{privilege.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('etaxApp.privilege.appCode')">App Code</span>
                    </dt>
                    <dd>
                        <span>{{privilege.appCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.privilege.userName')">User Name</span>
                    </dt>
                    <dd>
                        <span>{{privilege.userName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.privilege.profileName')">Profile Name</span>
                    </dt>
                    <dd>
                        <span>{{privilege.profileName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.privilege.limit')">Limit</span>
                    </dt>
                    <dd>
                        <span>{{privilege.limit}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.privilege.confirmStatus')">Confirm Status</span>
                    </dt>
                    <dd>
                        <span>{{privilege.confirmStatus}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.privilege.effectiveDate')">Effective Date</span>
                    </dt>
                    <dd>
                        <span>{{privilege.effectiveDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.privilege.expiryDate')">Expiry Date</span>
                    </dt>
                    <dd>
                        <span>{{privilege.expiryDate | formatDate}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="privilege.id" :to="{name: 'PrivilegeEdit', params: {privilegeId: privilege.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./privilege-details.component.ts">
</script>
