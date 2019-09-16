<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="etaxApp.exchangeRate.home.createOrEditLabel" v-text="$t('etaxApp.exchangeRate.home.createOrEditLabel')">Create or edit a ExchangeRate</h2>
                <div>
                    <div class="form-group" v-if="exchangeRate.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="exchangeRate.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.exchangeRate.cstdCurrencyA')" for="exchange-rate-cstdCurrencyA">Cstd Currency A</label>
                        <input type="text" class="form-control" name="cstdCurrencyA" id="exchange-rate-cstdCurrencyA"
                            :class="{'valid': !$v.exchangeRate.cstdCurrencyA.$invalid, 'invalid': $v.exchangeRate.cstdCurrencyA.$invalid }" v-model="$v.exchangeRate.cstdCurrencyA.$model"  required/>
                        <div v-if="$v.exchangeRate.cstdCurrencyA.$anyDirty && $v.exchangeRate.cstdCurrencyA.$invalid">
                            <small class="form-text text-danger" v-if="!$v.exchangeRate.cstdCurrencyA.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.exchangeRate.cstdCurrencyA.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.exchangeRate.cstdCurrencyB')" for="exchange-rate-cstdCurrencyB">Cstd Currency B</label>
                        <input type="text" class="form-control" name="cstdCurrencyB" id="exchange-rate-cstdCurrencyB"
                            :class="{'valid': !$v.exchangeRate.cstdCurrencyB.$invalid, 'invalid': $v.exchangeRate.cstdCurrencyB.$invalid }" v-model="$v.exchangeRate.cstdCurrencyB.$model"  required/>
                        <div v-if="$v.exchangeRate.cstdCurrencyB.$anyDirty && $v.exchangeRate.cstdCurrencyB.$invalid">
                            <small class="form-text text-danger" v-if="!$v.exchangeRate.cstdCurrencyB.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.exchangeRate.cstdCurrencyB.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.exchangeRate.rate')" for="exchange-rate-rate">Rate</label>
                        <input type="number" class="form-control" name="rate" id="exchange-rate-rate"
                            :class="{'valid': !$v.exchangeRate.rate.$invalid, 'invalid': $v.exchangeRate.rate.$invalid }" v-model.number="$v.exchangeRate.rate.$model"  required/>
                        <div v-if="$v.exchangeRate.rate.$anyDirty && $v.exchangeRate.rate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.exchangeRate.rate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.exchangeRate.rate.number" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.exchangeRate.startDate')" for="exchange-rate-startDate">Start Date</label>
                        <div class="input-group">
                            <input id="exchange-rate-startDate" type="date" class="form-control" name="startDate"  :class="{'valid': !$v.exchangeRate.startDate.$invalid, 'invalid': $v.exchangeRate.startDate.$invalid }"
                            v-model="$v.exchangeRate.startDate.$model"  required />
                        </div>
                        <div v-if="$v.exchangeRate.startDate.$anyDirty && $v.exchangeRate.startDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.exchangeRate.startDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.exchangeRate.endDate')" for="exchange-rate-endDate">End Date</label>
                        <div class="input-group">
                            <input id="exchange-rate-endDate" type="date" class="form-control" name="endDate"  :class="{'valid': !$v.exchangeRate.endDate.$invalid, 'invalid': $v.exchangeRate.endDate.$invalid }"
                            v-model="$v.exchangeRate.endDate.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.exchangeRate.ccVersion')" for="exchange-rate-ccVersion">Cc Version</label>
                        <input type="number" class="form-control" name="ccVersion" id="exchange-rate-ccVersion"
                            :class="{'valid': !$v.exchangeRate.ccVersion.$invalid, 'invalid': $v.exchangeRate.ccVersion.$invalid }" v-model.number="$v.exchangeRate.ccVersion.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.exchangeRate.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./exchange-rate-update.component.ts">
</script>
