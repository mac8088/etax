<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="etaxApp.bankAccount.home.createOrEditLabel" v-text="$t('etaxApp.bankAccount.home.createOrEditLabel')">Create or edit a BankAccount</h2>
                <div>
                    <div class="form-group" v-if="bankAccount.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="bankAccount.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.bankAccount.name')" for="bank-account-name">Name</label>
                        <input type="text" class="form-control" name="name" id="bank-account-name"
                            :class="{'valid': !$v.bankAccount.name.$invalid, 'invalid': $v.bankAccount.name.$invalid }" v-model="$v.bankAccount.name.$model"  required/>
                        <div v-if="$v.bankAccount.name.$anyDirty && $v.bankAccount.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.bankAccount.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.bankAccount.balance')" for="bank-account-balance">Balance</label>
                        <input type="number" class="form-control" name="balance" id="bank-account-balance"
                            :class="{'valid': !$v.bankAccount.balance.$invalid, 'invalid': $v.bankAccount.balance.$invalid }" v-model.number="$v.bankAccount.balance.$model"  required/>
                        <div v-if="$v.bankAccount.balance.$anyDirty && $v.bankAccount.balance.$invalid">
                            <small class="form-text text-danger" v-if="!$v.bankAccount.balance.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.bankAccount.balance.number" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('etaxApp.bankAccount.user')" for="bank-account-user">User</label>
                        <select class="form-control" id="bank-account-user" name="user" v-model="bankAccount.user">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="bankAccount.user && userOption.id === bankAccount.user.id ? bankAccount.user : userOption" v-for="userOption in users" :key="userOption.id">{{userOption.login}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.bankAccount.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./bank-account-update.component.ts">
</script>
