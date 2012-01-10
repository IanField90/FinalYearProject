class OptionsController < ApplicationController
  # GET /options/1
  def show
  end

  # GET /questions/1/options/new
  def new
    @question = Question.find(params[:question_id])
    @option = @question.options.new(params[:option])
    if is_user_admin
      render :new
    else
      redirect_to question_path(question)
    end
  end

  # GET /questions/1/options/edit
  def edit
  end

  # POST /options
  def create
    @option = Option.new(params[:option])
    if is_user_admin
      if @option.save
        redirect_to @option, :notice => "Option saved."
      else
        render :action => :new
      end
    else
      redirect_to @option
    end
  end
  
  # PUT /options
  def update
    if is_user_admin
      @option = Option.find(params[:id])
      if @option.update_attributes(params[:option])
        redirect_to @option, :notice => "Option successfully updated."
      else
        render :action => "edit"
      end
    else
      redirect_to parts_path, :notice => "Cannot edit unless admin."
    end
  end

  # DELETE /options
  def destroy
    @option = Option.find(params[:id])
    @id = @option.question_id
    if is_user_admin
      @option.destroy
      redirect_to question_path(@id)
    else
      redirect_to question_path(@id), :notice => "Cannot delete unless admin."
    end
  end

end
