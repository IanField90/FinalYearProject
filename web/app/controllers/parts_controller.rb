class PartsController < ApplicationController
  # GET /parts/1
  def show
    @part = Part.find(params[:id])
    respond_to do |format|
      format.html
      #Build full JSON response so it is just one request
      #If you're calling this then you already know the id of the part
      #TODO: Exclude unpublished quizzes - workaround - do not show on phone
      format.json {
        # @part = Part.find(params[:id], :include => {:quizzes => { :questions => :options  } }, :where =>  "quizzes.published"  )
         @part = Part.includes( :quizzes => { :questions => :options  }).where(["quizzes.published = ? AND parts.id = ?", true, params[:id]])
         render :json => @part, :include => {
          :materials => {
            :except => [ :created_at, :updated_at, :part_id, :material_type ]
          },
          :quizzes => { 
            :include => {
              :questions => {
                :include => { 
                  :options => { 
                    :except => [ :created_at, :updated_at, :question_id ] 
                  }
                },
                :except => [ :created_at, :updated_at, :quiz_id ]
              }
            },
            :except => [ :created_at, :updated_at, :user_id], #:part_id, :published ],
            # :conditions => { :published => true}
          }
        },
        :except => [ :created_at, :updated_at, :programme_id, :id,  :part_name_en, :part_name_fr, :part_name_es ] 
      }
    end
  end

  # GET /parts
  def index
    @parts = Part.all
    respond_to do |format|
      format.html
      format.json { render :json => @parts }
    end
  end
  
  # DELETE /parts/1
  def destroy
    @part = Part.find(params[:id])
    if is_user_admin
      @part.destroy
      redirect_to parts_path
    else
      redirect_to part_path, :notice => "Cannot delete unless admin"
    end
  end
  
  # GET /programmes/1/parts/new
  def new
    @programme = Programme.find(params[:programme_id])
    @part = @programme.parts.new(params[:part])
    if is_user_admin
      render :new
    else
      redirect_to part_path, :notice => "Cannot create unless admin"
    end
  end

  # POST /parts
  def create
    if is_user_admin
      @part = Part.new(params[:part])
      if @part.save
        redirect_to @part, :notice => 'Part was successfully created.'
      else
        render :action => "new"
      end
    else
      redirect_to parts_path
    end
  end

  # PUT /parts/1
  def update
    if is_user_admin
      #can update
      @part = Part.find(params[:id])
      if @part.update_attributes(params[:part])
        redirect_to @part, :notice => 'Part was successfully updated.'
      else
        render :action => "edit"
      end
    else
      redirect_to parts_path, "Cannot edit unless admin."
    end
  end
  
  # GET /parts/1/edit
  def edit
    @part = Part.find(params[:id])
    if !is_user_admin
      redirect_to part_path(@part), :notice => "Cannot edit unless admin"
    end
  end

end
