<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('etaxApp.bankAccount.home.title')" id="bank-account-heading">Bank Accounts</span>
            <router-link :to="{name: 'BankAccountCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-bank-account">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('etaxApp.bankAccount.home.createLabel')">
                    Create new BankAccount
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && bankAccounts && bankAccounts.length === 0">
            <span v-text="$t('etaxApp.bankAccount.home.notFound')">No bankAccounts found</span>
        </div>
        <div class="table-responsive" v-if="bankAccounts && bankAccounts.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('etaxApp.bankAccount.name')">Name</span></th>
                    <th><span v-text="$t('etaxApp.bankAccount.balance')">Balance</span></th>
                    <th><span v-text="$t('etaxApp.bankAccount.user')">User</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="bankAccount in bankAccounts"
                    :key="bankAccount.id">
                    <td>
                        <router-link :to="{name: 'BankAccountView', params: {bankAccountId: bankAccount.id}}">{{bankAccount.id}}</router-link>
                    </td>
                    <td>{{bankAccount.name}}</td>
                    <td>{{bankAccount.balance}}</td>
                    <td>
                        {{bankAccount.user ? bankAccount.user.login : ''}}
                    </td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'BankAccountView', params: {bankAccountId: bankAccount.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'BankAccountEdit', params: {bankAccountId: bankAccount.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(bankAccount)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="etaxApp.bankAccount.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-bankAccount-heading" v-bind:title="$t('etaxApp.bankAccount.delete.question')">Are you sure you want to delete this Bank Account?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-bankAccount" v-text="$t('entity.action.delete')" v-on:click="removeBankAccount()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./bank-account.component.ts">
</script>
