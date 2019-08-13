<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="bankAccount">
                <h2 class="jh-entity-heading"><span v-text="$t('etaxApp.bankAccount.detail.title')">BankAccount</span> {{bankAccount.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('etaxApp.bankAccount.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{bankAccount.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.bankAccount.balance')">Balance</span>
                    </dt>
                    <dd>
                        <span>{{bankAccount.balance}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('etaxApp.bankAccount.user')">User</span>
                    </dt>
                    <dd>
                        {{bankAccount.user ? bankAccount.user.login : ''}}
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="bankAccount.id" :to="{name: 'BankAccountEdit', params: {bankAccountId: bankAccount.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./bank-account-details.component.ts">
</script>
