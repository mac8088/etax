<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="etaxApp.officeRelationship.home.createOrEditLabel" v-text="$t('etaxApp.officeRelationship.home.createOrEditLabel')">Create or edit a OfficeRelationship</h2>
                <div>
                    <div class="form-group" v-if="officeRelationship.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="officeRelationship.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.officeRelationship.parentId')" for="office-relationship-parentId">Parent Id</label>
                        <input type="number" class="form-control" name="parentId" id="office-relationship-parentId"
                            :class="{'valid': !$v.officeRelationship.parentId.$invalid, 'invalid': $v.officeRelationship.parentId.$invalid }" v-model.number="$v.officeRelationship.parentId.$model"  required/>
                        <div v-if="$v.officeRelationship.parentId.$anyDirty && $v.officeRelationship.parentId.$invalid">
                            <small class="form-text text-danger" v-if="!$v.officeRelationship.parentId.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.officeRelationship.parentId.number" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.officeRelationship.chileId')" for="office-relationship-chileId">Chile Id</label>
                        <input type="number" class="form-control" name="chileId" id="office-relationship-chileId"
                            :class="{'valid': !$v.officeRelationship.chileId.$invalid, 'invalid': $v.officeRelationship.chileId.$invalid }" v-model.number="$v.officeRelationship.chileId.$model"  required/>
                        <div v-if="$v.officeRelationship.chileId.$anyDirty && $v.officeRelationship.chileId.$invalid">
                            <small class="form-text text-danger" v-if="!$v.officeRelationship.chileId.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.officeRelationship.chileId.number" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.officeRelationship.startDate')" for="office-relationship-startDate">Start Date</label>
                        <div class="d-flex">
                            <input id="office-relationship-startDate" type="datetime-local" class="form-control" name="startDate" :class="{'valid': !$v.officeRelationship.startDate.$invalid, 'invalid': $v.officeRelationship.startDate.$invalid }"
                             required
                            :value="convertDateTimeFromServer($v.officeRelationship.startDate.$model)"
                            @change="updateZonedDateTimeField('startDate', $event)"/>
                        </div>
                        <div v-if="$v.officeRelationship.startDate.$anyDirty && $v.officeRelationship.startDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.officeRelationship.startDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.officeRelationship.startDate.ZonedDateTimelocal" v-text="$t('entity.validation.ZonedDateTimelocal')">
                                This field should be a date and time.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.officeRelationship.endDate')" for="office-relationship-endDate">End Date</label>
                        <div class="d-flex">
                            <input id="office-relationship-endDate" type="datetime-local" class="form-control" name="endDate" :class="{'valid': !$v.officeRelationship.endDate.$invalid, 'invalid': $v.officeRelationship.endDate.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.officeRelationship.endDate.$model)"
                            @change="updateZonedDateTimeField('endDate', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.officeRelationship.ccVersion')" for="office-relationship-ccVersion">Cc Version</label>
                        <input type="number" class="form-control" name="ccVersion" id="office-relationship-ccVersion"
                            :class="{'valid': !$v.officeRelationship.ccVersion.$invalid, 'invalid': $v.officeRelationship.ccVersion.$invalid }" v-model.number="$v.officeRelationship.ccVersion.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.officeRelationship.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./office-relationship-update.component.ts">
</script>
